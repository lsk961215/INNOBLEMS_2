<template>
    <div class="wrap">
		<div class="pageTitle"><h1>프로젝트 관리</h1></div>
		<div class="middle">
			<asideMenu></asideMenu>
			<section>
				<div class="filter"> 
					<div class="filterTitle"><h1>검색 조건</h1></div>
					<div class="filterDetail">
						<div class="filterSection_1">
							<small>프로젝트 번호</small>
							<input name="prjSeq" id="prjSeq" type="text" v-model="requestBody.prjSeq">
							<small>프로젝트명</small>
							<input name="prjNm" id="prjNm" type="text" v-model="requestBody.prjNm">
							<small>고객사명</small>
							<select name="cusCD" id="cusCD" v-model="requestBody.cusCD">
								<option value="0">선택</option>
								<option v-for="item in cusCDs" :key="item.id" :value="item.dtCD">{{ item.dtCDNM }}</option>
							</select>
						</div>
						<div class="filterSection_2">
							<small class="inDT">시작일</small> <input id="minDT" type="date" max="9999-12-31" v-model="requestBody.minSTDT"> ~ <input id="maxDT" type="date" max="9999-12-31" v-model="requestBody.maxSTDT">
							<small class="inDT">종료일</small> <input id="minDT" type="date" max="9999-12-31" v-model="requestBody.minEDDT"> ~ <input id="maxDT" type="date" max="9999-12-31" v-model="requestBody.maxEDDT">
						</div>
						<div class="filterSection_3">
							<small class="skillText">보유기술</small> 
							<template v-for="item in skCDs" :key="item.dtCDNM">
								<input type="checkbox" class="skill" :id="item.dtCD" :value="item.dtCD" v-model="requestBody.skillList">
								<label :for="item.dtCD">{{ item.dtCDNM }} </label>
							</template>
						</div>
						<div class="filterSection_4">
							<button id="search" v-on:click="getProjectList()">조회</button>
						</div>
					</div>
				</div>
				<div class="result">
					<div class="resultTitle"><h1>검색 결과</h1></div>
					<div class="resultPerPage">
						<select name="countPerPage" id="countPerPage" v-model="requestBody.countPerPage">
							<option value="5">5개씩 보기</option>
							<option value="10">10개씩 보기</option>
						</select>
					</div>
					<div class="resultDetail">
						<table>
							<thead>
								<tr>
									<th class="checkHead"><input type="checkbox" id="checkAll" v-model="allSelected"></th>
									<th class="numberHead">프로젝트 번호</th>
									<th class="nameHead">프로젝트명</th>
									<th class="customerHead">고객사명</th>
									<th class="skillsHead">필요기술</th>
									<th class="startHead">시작일</th>
									<th class="endHead">종료일</th>
									<th class="editHead">상세/수정</th>
									<th class="userHead">인원 관리</th>
								</tr>
							</thead>
							<tbody id="tbody" v-if="projectList.length == 0">
								<tr>
									<td colspan="10"><h2>검색결과가 없습니다.</h2></td>
								</tr>
							</tbody>
							<tbody id="tbody" v-else-if="projectList.length == null">
								<tr>
									<td colspan="10"><h2>데이터가 없습니다.</h2></td>
								</tr>
							</tbody>
							<tbody id="tbody" v-else>
								<tr v-for="item in projectList" :key="item.prjSeq">
									<td><input type="checkbox" :value="item.prjSeq" v-model="selectList"></td>
									<td>{{ item.prjSeq }}</td>
									<td>{{ item.prjNm }}</td>
									<td>{{ item.cusCD }}</td>
									<td>{{ item.skills }}</td>
									<td>{{ item.prjSTDT }}</td>
									<td>{{ item.prjEDDT }}</td>
									<td><input type="button" value="상세/수정" :id="item.prjSeq" v-on:click="getProjectDetail($event)"></td>
									<td><input type="button" value="인원 관리"></td>
								</tr>
							</tbody>
						</table>
					</div>
					{{ requestBody }}
					<div class="resultPage">
						<button v-if="page.beginPaging >= page.groupCount" v-on:click="getProejctList(page.beginPaging - 1)">이전</button>
						<button v-for="(item, index) in page.endPaging - (page.groupCount * (page.position - 1))" :key="index" :class="{'currentPage': page.beginPaging + index == page.pageNum }" v-on:click="getProjectList(page.beginPaging + index)"> {{ page.beginPaging + index }}</button>
						<button v-if="page.endPaging <= page.totalPaging - page.groupCount * (page.position - 1) && page.totalPaging > page.groupCount" v-on:click="getProjectList(page.endPaging + 1)">다음</button>
					</div>
					<div class="resultButtonWrap">
						<div class="resultButton">
							{{ selectList }}
							<button v-if="projectList.length > 0" v-on:click="add()" >등록</button>
							<button v-if="projectList.length > 0" v-on:click="del()">삭제</button>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
</template>

<script>

import axios from 'axios';
import asideMenu from '../components/asideMenu.vue'

export default {

	name: 'App',

	data () {
		return {
			requestBody: {
				prjSeq: '',
				prjNm: '',
				cusCD: 0,
				pageNum: '',
				skillList: [],
				skills: '',
				prjSTDT: '',
				prjEDDT: '',
				prjNote: '',
				minSTDT: '',
				maxSTDT: '',
				minEDDT: '',
				maxEDDT: '',
				countPerPage: 5
			},
			projectList: {

			},
			selectList: [],
			page: {
				beginPaging: '',
				totalPaging: '',
				endPaging: '',
				pageNum: '',
				groupCount: '',
				position: ''
			},
		}
	},

	components: {
		asideMenu: asideMenu,
	},

	computed: {
		grCDs: function () {
		const arr = this.$codeList.data
		const grCDs = arr.filter(item => item.mstCD == "GR01")
		return grCDs
		},

		stCDs: function () {
		const arr = this.$codeList.data
		const stCDs = arr.filter(item => item.mstCD == "ST01")
		return stCDs
		},

		skCDs: function () {
		const arr = this.$codeList.data
		const skCDs = arr.filter(item => item.mstCD == "SK01")
		return skCDs
		},

		raCDs: function () {
		const arr = this.$codeList.data
		const raCDs = arr.filter(item => item.mstCD == "RA01")
		return raCDs
		},

		cusCDs: function () {
		const arr = this.$codeList.data
		const cusCDs = arr.filter(item => item.mstCD == "CU01")
		return cusCDs
		},

		allSelected: {
		//getter
		get: function() {
			return (this.projectList.length === this.selectList.length && this.projectList.length != 0);
		},
		//setter
		set: function(e) {
			this.selectList = e ? this.projectList.map(row=>row.prjSeq) : [];
		},
		},
	},
	
	methods : {
		getProjectList: function(pageNum){
			this.requestBody.skillList.sort()
			this.requestBody.skills = this.requestBody.skillList.toString()
			this.requestBody.pageNum = pageNum
			
			var vm = this

			axios.post('http://localhost:8080/getProjectList', vm.requestBody)
			.then(function (response) {
				vm.projectList = vm.parseProjectList(response.data.projectList)

				vm.page.beginPaging = response.data.beginPaging
				vm.page.endPaging = response.data.endPaging
				vm.page.pageNum = response.data.pageNum
				vm.page.totalPaging = response.data.totalPaging
				vm.page.groupCount = response.data.groupCount
				vm.page.position = response.data.position
			})
			.catch(function (error) {
				console.log(error)
			})
		},

		parseProjectList: function(projectList) {
			var codeList = this.$codeList.data

			for(var i = 0; i<projectList.length; i++){
				var skillArr = projectList[i].skills.split(",")
				projectList[i].skills = ""

				for(var j = 0; j<codeList.length; j++){
					if(codeList[j].mstCD == "CU01" && projectList[i].cusCD == codeList[j].dtCD){
						projectList[i].cusCD = codeList[j].dtCDNM
					}
					for(var k = 0; k<skillArr.length; k++){
						if(codeList[j].mstCD == "SK01" && skillArr[k] == codeList[j].dtCD){
							if(k != 0){
								projectList[i].skills += ", "
							}
							projectList[i].skills += codeList[j].dtCDNM
						}
					}
				}
			}

			return projectList
		},

		add: function(){
			window.open("addProject", "openForm", "width=1000px height=600px");
		},

		getProjectDetail: function(event){
			window.open("projectDetail?prjSeq="+event.currentTarget.id, "openForm", "width=1000px height=600px");
		},

		del: function(){
			var vm = this
			axios.post('http://localhost:8080/delProject', this.selectList)
			.then(function (response) {
				if(response.data == 0){
					alert("삭제 되었습니다.")
					vm.selectList = []
				} else {
					alert("해당 프로젝트는 현재 소속되어있는 인원이 있습니다.")
				}
			})
			.catch(function (error) {
				console.log(error)
			})
		}
	}
}
</script>
  
<style>
main {
	display: flex;
	max-width: 1240px;
  margin: 0 auto;
}
	
section {
	background-color: white;
	width: 75%;
			
	display: flex;
	flex-direction: column;
	
	margin-top: 50px;
	margin-bottom: 50px;
	margin-right: 50px;
}

button {
	border: none;
	background-color: white;
	cursor: pointer;
}

input[type=text], select {
	width: 100px;
}
	
.middle {
	display: flex;
	
	margin-bottom: 50px;
	
	border: 2px solid lightgrey;
	
	width: 1240px;
}
	
.pageTitle {
	display: flex;
	align-items: center;
	background-color: white;
	
	padding-left: 10px;
	padding-right: 10px;
		
	height: 50px;
		
	position: relative;
	top: 25px;
	left: 20px;
	
	width: max-content;
	
	font-size: 150%;
}

.filterTitle {
	display: flex;
	align-items: center;
	background-color: white;
	
	padding-left: 10px;
	padding-right: 10px;
		
	height: 30px;
		
	position: relative;
	top: -15px;
	left: -10px;
	
	width: max-content;
	
	font-size: 150%;
}

.resultTitle {
	display: flex;
	align-items: center;
	background-color: white;
	
	padding-left: 10px;
	padding-right: 10px;
		
	position: relative;
	top: -15px;
	left: -10px;
	
	width: max-content;
	height: 30px;
	
	font-size: 150%;
}

.resultDetail {
	width:100%;
	overflow: auto;
}

.filter {
	margin-bottom: 50px;
	
	padding-left: 30px;
	padding-right: 30px;
	padding-bottom: 30px;
	
	border: 2px solid lightgrey;
}

.result {
	padding-left: 30px;
	padding-right: 30px;
	padding-bottom: 30px;
	
	border: 2px solid lightgrey;
}

.filterSection_1 {
	display: flex;
	justify-content: space-between;
	
	margin-bottom: 10px;
}

.filterSection_3 {
	display: flex;
	justify-content: space-between;
	
	margin-top: 10px;
	margin-bottom: 20px;
}

.filterSection_4 {
	display: flex;
	justify-content: center;
}

.inDT {
	margin-left: 6px;
	margin-right: 37px;
}

.skillText {
	margin-right: 5px;
}

#search {
	border: none;
	background-color: #0C70F2;
	color: white;
	
	font-weight: bold;
	font-size: 105%;
	
	width: 80px;
	height: 30px;
	
	cursor: pointer;
}

#add {
	display: flex;
	justify-content: center;
	align-items: center;

	border: none;
	background-color: #0C70F2;
	color: white;
	
	font-weight: bold;
	font-size: 105%;
	
	width: 80px;
	height: 30px;
	
	cursor: pointer;
	
	margin-right: 50px;
	
	text-decoration: none;
}

#del {
	border: none;
	background-color: red;
	color: white;
	
	font-weight: bold;
	font-size: 105%;
	
	width: 80px;
	height: 30px;
	
	cursor: pointer;
}

#edit {
	display: flex;
	justify-content: center;
	align-items: center;

	border: none;
	background-color: #0C70F2;
	color: white;
	
	font-weight: bold;
	font-size: 80%;
	
	width: 100%;
	height: 18px;
	
	cursor: pointer;
	
	text-decoration: none;
}

#project {
	display: flex;
	justify-content: center;
	align-items: center;

	border: none;
	background-color: #0C70F2;
	color: white;
	
	font-weight: bold;
	font-size: 80%;
	
	width: 100%;
	height: 18px;
	
	cursor: pointer;
	
	text-decoration: none;
}

table {
	margin-bottom: 1%;
		
	border-collapse : collapse;
}

table td, table th{
  padding-left: 5px;
	padding-right: 5px;
	
	text-align: center;
		
	border: 2px solid lightgrey;
}
	
table th {
	background-color: #E4E4E4;
	color: black;
}

table .checkRow {
	min-width: 50px;
	text-align: center;
}
table .checkHead {
	min-width: 50px;
	text-align: center;
}

table .numberRow {
	min-width: 100px;
	text-align: right;
}

table .numberHead {
	min-width: 100px;
	text-align: center;
}

table .inDateRow, table .rankRow, table .gradeRow, table .statusRow {
	min-width: 100px;
	text-align: center;
}

table .inDateHead, table .rankHead, table .gradeHead, table .statusHead {
	min-width: 100px;
	text-align: center;
}

table .nameRow {
	min-width: 150px;
	text-align: left;
}

table .nameHead {
	min-width: 150px;
	text-align: center;
}

table .skillsRow {
	min-width: 400px;
	text-align: left;
}

table .skillsHead {
	min-width: 400px;
	text-align: center;
}

table .editRow {
	min-width: 100px;
	text-align: center;
}

table .editHead {
	min-width: 100px;
	text-align: center;
}

table .projectRow {
	min-width: 100px;
	text-align: center;
}

table .projectHead {
	min-width: 100px;
	text-align: center;
}

.resultPage {
	display: flex;
	justify-content: center;
}

.resultButtonWrap {
	display:flex;
	justify-content: center;
}

.resultButton {
	display:flex;
	justify-content: center;
}

.resultPerPage {
	display: flex;
	flex-direction: row-reverse;
	margin-bottom: 10px;
}

.currentPage {
	font-size: 2em;
}

</style>
  