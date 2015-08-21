/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.aliyuncs;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.auth.Credential;
import com.aliyuncs.auth.ISigner;
import com.aliyuncs.auth.RpcSignatureComposer;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpRequest;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.regions.ProductDomain;

public abstract class RpcAcsRequest<T extends AcsResponse> extends AcsRequest<T> {
	
	public RpcAcsRequest(String product) {
		super(product);
		initialize();
	}
	
	public RpcAcsRequest(String product, String version) {
		super(product);
		this.setVersion(version);
		initialize();
	}
	
	public RpcAcsRequest(String product, String version, String action) {
		super(product);
		this.setVersion(version);
		this.setActionName(action);
		initialize();
	}
	
	private void initialize() {
		this.setMethod(MethodType.GET);
		this.setAcceptFormat(FormatType.XML);
		this.composer = RpcSignatureComposer.getComposer();
	}
	
	@Override
	public void setActionName(String actionName) {
		super.setActionName(actionName);
		this.putQueryParameter("Action", actionName);
	}
	
	@Override
	public void setVersion(String version) {
		super.setVersion(version);
		this.putQueryParameter("Version", version);
	}
	
	@Override
	public void setAcceptFormat(FormatType acceptFormat) {
		super.setAcceptFormat(acceptFormat);
		this.putQueryParameter("Format", acceptFormat.toString());
	}
	
	@Override
	protected void setParameter(Map<String, String> map, String name, String value) {
		if (null == name || null == value){
			return;
		}
		map.put(name, value);
	}
	
	public String composeUrl(String endpoint, Map<String, String> queries) 
			throws UnsupportedEncodingException{
		Map<String, String> mapQueries = (queries == null) ? this.getQueryParameters():queries;
		StringBuilder urlBuilder = new StringBuilder("");
		urlBuilder.append(this.getProtocol().toString());
		urlBuilder.append("://").append(endpoint);
		if (-1 == urlBuilder.indexOf("?")){
			urlBuilder.append("/?");
		}
		String query = concatQueryString(mapQueries);
		return urlBuilder.append(query).toString();
	}
	
	@Override
	public HttpRequest signRequest(ISigner signer, Credential credential,FormatType format, ProductDomain domain) 
			throws InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
		Map<String, String> imutableMap = new HashMap<String, String>(this.getQueryParameters());
		if (null != signer && null != credential) {
			String accessKeyId = credential.getAccessKeyId();
			String accessSecret = credential.getAccessSecret();
			imutableMap = this.composer.refreshSignParameters
					(this.getQueryParameters(), signer, accessKeyId, format);
			imutableMap.put("RegionId", getRegionId());
			String strToSign = this.composer.composeStringToSign
				(this.getMethod(), null, signer, imutableMap, null, null);
			String signature = signer.signString(strToSign, accessSecret + "&");
			imutableMap.put("Signature", signature);
		}
		HttpRequest request = new HttpRequest(this.composeUrl(domain.getDomianName(), imutableMap), this.getHeaders());
		request.setMethod(this.getMethod());
		return request;
	}
}
