/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aliyuncs.ga.model.v20191120;

import java.util.List;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.ga.transform.v20191120.ListIpSetsResponseUnmarshaller;
import com.aliyuncs.transform.UnmarshallerContext;

/**
 * @author auto create
 * @version 
 */
public class ListIpSetsResponse extends AcsResponse {

	private String requestId;

	private Integer totalCount;

	private Integer pageNumber;

	private Integer pageSize;

	private List<IpSetsItem> ipSets;

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<IpSetsItem> getIpSets() {
		return this.ipSets;
	}

	public void setIpSets(List<IpSetsItem> ipSets) {
		this.ipSets = ipSets;
	}

	public static class IpSetsItem {

		private String ipSetId;

		private String accelerateRegionId;

		private Integer bandwidth;

		private String state;

		private List<String> ipAddressList;

		public String getIpSetId() {
			return this.ipSetId;
		}

		public void setIpSetId(String ipSetId) {
			this.ipSetId = ipSetId;
		}

		public String getAccelerateRegionId() {
			return this.accelerateRegionId;
		}

		public void setAccelerateRegionId(String accelerateRegionId) {
			this.accelerateRegionId = accelerateRegionId;
		}

		public Integer getBandwidth() {
			return this.bandwidth;
		}

		public void setBandwidth(Integer bandwidth) {
			this.bandwidth = bandwidth;
		}

		public String getState() {
			return this.state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public List<String> getIpAddressList() {
			return this.ipAddressList;
		}

		public void setIpAddressList(List<String> ipAddressList) {
			this.ipAddressList = ipAddressList;
		}
	}

	@Override
	public ListIpSetsResponse getInstance(UnmarshallerContext context) {
		return	ListIpSetsResponseUnmarshaller.unmarshall(this, context);
	}

	@Override
	public boolean checkShowJsonItemName() {
		return false;
	}
}
