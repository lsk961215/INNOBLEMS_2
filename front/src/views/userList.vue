<template>
    <div class="wrap">
		<div class="pageTitle"><h1>사원 관리</h1></div>
		<div class="middle">
			<asideMenu></asideMenu>
			<section>
				<div class="filter"> 
					<div class="filterTitle"><h1>검색 조건</h1></div>
					<div class="filterDetail">
						<div class="filterSection_1">
							<small>사원번호</small>
							<input name="usrSeq" id="usrSeq" type="text" v-model="requestBody.usrSeq">
							<small>사원명</small>
							<input name="usrNm" id="usrNm" type="text" v-model="requestBody.usrNm">
							<small>기술등급</small>
							<select name="grCD" id="grCD" v-model="requestBody.grCD">
								<option value="0">선택</option>
								<option v-for="item in grCDs" :key="item.id" :value="item.dtCD">{{ item.dtCDNM }}</option>
							</select>
							<small>재직상태</small>
							<select name="stCD" id="stCD" v-model="requestBody.stCD">
								<option value="0">선택</option>
								<option v-for="item in stCDs" :key="item.id" :value="item.dtCD">{{ item.dtCDNM }}</option>
							</select>
						</div>
						<div class="filterSection_2">
							<small class="inDT">입사일</small> <input id="minDT" type="date" max="9999-12-31" v-model="requestBody.minDT"> ~ <input id="maxDT" type="date" max="9999-12-31" v-model="requestBody.maxDT">
						</div>
						<div class="filterSection_3">
							<small class="skillText">보유기술</small> 
							<template v-for="item in skCDs" :key="item.dtCDNM">
								<input type="checkbox" class="skill" :id="item.dtCD" :value="item.dtCD" v-model="requestBody.skillList">
								<label :for="item.dtCD">{{ item.dtCDNM }} </label>
							</template>
						</div>
						<div class="filterSection_4">
							<button id="search" v-on:click="getUserList()">조회</button>
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
									<th class="checkHead"><input type="checkbox" id="checkAll"></th>
									<th class="numberHead">사원번호</th>
									<th class="inDateHead">입사일</th>
									<th class="rankHead">직급</th>
									<th class="nameHead">사원명</th>
									<th class="gradeHead">기술등급</th>
									<th class="skillsHead">보유기술</th>
									<th class="statusHead">재직상태</th>
									<th class="editHead">상세/수정</th>
									<th class="projectHead">프로젝트 관리</th>
								</tr>
							</thead>
							<tbody id="tbody" v-if="userList.length == 0">
								<tr>
									<td colspan="10"><h2>검색결과가 없습니다.</h2></td>
								</tr>
							</tbody>
							<tbody id="tbody" v-else-if="userList.length == null">
								<tr>
									<td colspan="10"><h2>데이터가 없습니다.</h2></td>
								</tr>
							</tbody>
							<tbody id="tbody" v-else>
								<tr v-for="item in userList" :key="item.usrSeq">
									<td><input type="checkbox"></td>
									<td>{{ item.usrSeq }}</td>
									<td>{{ item.usrINDT }}</td>
									<td>{{ item.raCD }}</td>
									<td>{{ item.usrNm }}</td>
									<td>{{ item.grCD }}</td>
									<td>{{ item.skills }}</td>
									<td>{{ item.stCD }}</td>
									<td><input type="button" value="상세/수정" :id="item.usrSeq" v-on:click="getUserDetail($event)"></td>
									<td><input type="button" value="프로젝트 관리"></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="resultPage">
						<button  v-for="(item, index) in page.totalPaging" :key="index" :class="{'currentPage': page.beginPaging + index == page.pageNum }" v-on:click="getUserList(page.beginPaging + index)"> {{ page.beginPaging + index }}</button>
					</div>
					<div class="resultButtonWrap">
						<div class="resultButton">
							<button v-if="userList.length > 0" v-on:click="add()" >등록</button>
							<button v-if="userList.length > 0" >삭제</button>
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
				usrSeq: '',
				usrNm: '',
				grCD: 0,
				stCD: 0,
				pageNum: '',
				skillList: [],
				skills: '',
				minDT: '',
				maxDT: '',
				countPerPage: 5
			},
			userList: {

			},
			page: {
				beginPaging: '',
				totalPaging: '',
				endPaging: '',
				pageNum: ''
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
		}
	},
	
	methods : {
		getUserList: function(pageNum){
			this.requestBody.skillList.sort()
			this.requestBody.skills = this.requestBody.skillList.toString()
			this.requestBody.pageNum = pageNum
			
			var vm = this

			axios.post('http://localhost:8080/getUserList', this.requestBody)
			.then(function (response) {
				console.log(response.data.userList)
				vm.userList = vm.parseUserList(response.data.userList)

				vm.page.beginPaging = response.data.beginPaging
				vm.page.endPaging = response.data.endPaging
				vm.page.pageNum = response.data.pageNum
				vm.page.totalPaging = response.data.totalPaging
			})
			.catch(function (error) {
				console.log(error)
			})
		},

		parseUserList: function(userList) {
			var codeList = this.$codeList.data

			for(var i = 0; i<userList.length; i++){
				var skillArr = userList[i].skills.split(",")
				userList[i].skills = ""

				for(var j = 0; j<codeList.length; j++){
					if(codeList[j].mstCD == "RA01" && userList[i].raCD == codeList[j].dtCD){
						userList[i].raCD = codeList[j].dtCDNM
					}
					if(codeList[j].mstCD == "GR01" && userList[i].grCD == codeList[j].dtCD){
						userList[i].grCD = codeList[j].dtCDNM
					}
					if(codeList[j].mstCD == "ST01" && userList[i].stCD == codeList[j].dtCD){
						userList[i].stCD = codeList[j].dtCDNM
					}
					for(var k = 0; k<skillArr.length; k++){
						if(codeList[j].mstCD == "SK01" && skillArr[k] == codeList[j].dtCD){
							if(k != 0){
								userList[i].skills += ", "
							}
							userList[i].skills += codeList[j].dtCDNM
						}
					}
				}
			}

			return userList
		},

		add: function(){
			window.open("goAddUser", "openForm", "width=1000px height=600px");
		},

		getUserDetail: function(event){
			window.open("userDetail?usrSeq="+event.currentTarget.id, "openForm", "width=1000px height=600px");
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
  