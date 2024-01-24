<template>
    <main>
        <section>
            <div class="pageTitle"><h1>사원 등록</h1></div>
            <div class="sectionMain">
                <div class=imgSection>
                    <input type="file" id="image" @change="changeImage">
                    <img class="image-box" src="@/assets/default.png">
                </div>
                <div class="detailSection">
                    <small class="essential"><a class="star">*</a>는 필수항목</small>
                    <table>
                        <tr>
                            <td>사원명<a class="star">*</a></td>
                            <td><input type="text" v-model="requestBody.usrNm" id="usrNm" maxlength="10" style="ime-mode:active;" placeholder="한글 10글자 이내"></td>
                            <td>입사일<a class="star">*</a></td>
                            <td><input type="date" v-model="requestBody.usrINDT" id="usrINDT"></td>
                        </tr> 
                        <tr>
                            <td>아이디<a class="star">*</a></td>
                            <td><input type="text" v-model="requestBody.usrId" id="usrId" maxlength="20" placeholder="영문 20글자 이내"></td>
                            <td>직급</td>
                            <td>
                                <select name="raCD" v-model="requestBody.raCD" id="raCD">
                                    <option value="0">선택</option>
                                    <option v-for="item in raCDs" :key="item.id" :value="item.dtCD">{{ item.dtCDNM }}</option>
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td>비밀번호<a class="star">*</a></td>
                            <td><input :type="pwType" v-model="requestBody.usrPw" id="usrPw" maxlength="16" placeholder="영문,특수문자 포함 16글자 이내"></td>
                            <td>기술등급</td>
                            <td>
                                <select name="grCD" v-model="requestBody.grCD" id="grCD">
                                    <option value="0">선택</option>
                                    <option v-for="item in grCDs" :key="item.id" :value="item.dtCD">{{ item.dtCDNM }}</option>
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td>비밀번호 확인<a class="star">*</a></td>
                            <td><input :type="pwType" v-model="requestBody.usrPwCheck" id="usrPwCheck" maxlength="16" placeholder="영문,특수문자 포함 16글자 이내"></td>
                            <td>개발분야</td>
                            <td>
                                <select name="dvCD" v-model="requestBody.dvCD" id="dvCD">
                                    <option value="0">선택</option>
                                    <option v-for="item in dvCDs" :key="item.id" :value="item.dtCD">{{ item.dtCDNM }}</option>
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td colspan="2">
                                <button v-on:click="pwView">보기</button>
                                <div id="checkText">
                                </div>
                            </td>
                            <td>전화번호<a class="star">*</a></td>
                            <td><input type="text" v-model="requestBody.usrPn" id="usrPn" placeholder="숫자만 입력해주세요" maxlength="11" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"></td>
                        </tr> 
                        <tr>
                            <td>생년월일</td>
                            <td><input type="date" v-model="requestBody.usrBDT" id="usrBDT"></td>
                            <td>이메일</td>
                            <td><input type="text" v-model="requestBody.usrEm" id="usrEm" placeholder="exmaple@example.com 형식으로 입력해주세요"></td>
                        </tr> 
                        <tr>
                            <td>성별</td>
                            <td>
                                <select name="gdCD" v-model="requestBody.gdCD" id="gdCD">
                                    <option value="0">선택</option>
                                    <option v-for="item in gdCDs" :key="item.id" :value="item.dtCD">{{ item.dtCDNM }}</option>
                                </select>
                            </td>
                            <td colspan="2"></td>
                        </tr> 
                        <tr>
                            <td>주소</td>
                            <td colspan="3">				    			
                                <input type="text" v-model="requestBody.zonecode" placeholder="우편번호" readonly>
                                <input type="button" id="search" v-on:click="daumMap" value="주소 찾기"><br>
                                <input type="text" v-model="requestBody.roadAddress" placeholder="도로명 주소" readonly>
                                <input type="text" v-model="requestBody.detailAddress" placeholder="상세 주소" maxlength="100">
                            </td>
                            <div>
                                
                            </div>
                        </tr> 
                        <tr>
                            <td>보유기술<a class="star">*</a></td>
                            <td colspan="3">
                                <div class="skills">
                                    <template v-for="item in skCDs" :key="item.dtCDNM">
                                        <input type="checkbox" class="skill" :id="item.dtCD" :value="item.dtCD" v-model="requestBody.skillList">
                                        <label :for="item.dtCD">{{ item.dtCDNM }} </label>
                                    </template>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="buttonSection">
                <button id="add" v-on:click="add">등록</button>
                <button id="cancel" v-on:click="cancel">취소</button>
            </div>
        </section>
    </main>
</template>

<script>
import axios from "axios"

export default {
    name: "addUser",

    data() {
        return {
            requestBody: {
                usrImg: '',
				usrSeq: '',
                usrNm: '',
                usrINDT: '',
                usrId: '',
                raCD: '',
                usrPw: '',
                usrPwCheck: '',
				grCD: '',
				stCD: '',
                dvCD: '',
                usrPn: '',
                usrBDT: '',
                usrEm: '',
                gdCD: '',
				pageNum: '',
				skillList: [],
				skills: '',
				minDT: '',
				maxDT: '',
				countPerPage: 5,
                zonecode: '',
                roadAddress: '',
                detailAddress: ''
            },

            pwType: 'password',

            image: [],
        };
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

        dvCDs: function () {
        const arr = this.$codeList.data
        const dvCDs = arr.filter(item => item.mstCD == "DV01")
        return dvCDs
        },

        gdCDs: function () {
        const arr = this.$codeList.data
        const gdCDs = arr.filter(item => item.mstCD == "GD01")
        return gdCDs
        }
    },

    methods: {
        get: function() {
            axios
            .get(`http://localhost:8080/getCodeList`)
            .then((result) => {
            console.log(result.data);
            this.data.push(result.data);
            this.id++;
            })
            .catch((error) => {
            console.log(error);
            })
            .finally(() => {
            console.log("finally");
            });
        },

        daumMap: function() {
            new window.daum.Postcode({
                oncomplete: (data) => {
                    this.requestBody.zonecode = data.zonecode
                    this.requestBody.roadAddress = data.roadAddress
                },
            }).open();
        },

        pwView: function() {
            if(this.pwType == "password"){
                this.pwType = "text"
            } else {
                this.pwType = "password"
            }
        },

        add: function() {
            axios.post('http://localhost:8080/addUser', this.requestBody)
			.then(function (response) {
                if(response.data == 0){
                    alert("등록되었습니다.")
                } else if(response.data == 1){
                    alert("등록할 수 없습니다.")
                }
			})
			.catch(function (error) {
				console.log(error)
			})
        },

        cancel: function() {
            window.close()
        },

        changeImage: function() {
            const fileDOM = document.querySelector('#image');
            const preview = document.querySelector('.image-box');

            if(fileDOM.files.length != 0){
                const reader = new FileReader();
                reader.onload = ({ target }) => {
                    preview.src = target.result;
                };
                reader.readAsDataURL(fileDOM.files[0]);
            }
        },

        uploadImage: function() {
            const formData = new FormData()
            const fileInput = document.getElementById('image')

            formData.append('image', fileInput.files[0])

            axios.post('http://localhost:8080/addImage', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
			.then(function (response) {
                if(response != -1){
                    alert("등록되었습니다."+response.data)
                } else if(response == -1){
                    alert("등록할 수 없습니다.")
                }
			})
			.catch(function (error) {
				console.log(error)
			})
        }
    },
};
</script>
  
<style scoped>
</style>
  