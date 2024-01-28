<template>
    <main>
        <section>
            <div class="pageTitle"><h1>프로젝트 등록</h1></div>
            <div class="sectionMain">
                <div class="detailSection">
                    <small class="essential"><a class="star">*</a>는 필수항목</small>
                    <table>
                        <tr>
                            <td>프로젝트명<a class="star">*</a></td>
                            <td><input type="text" v-model="requestBody.prjNm" id="prjNm" maxlength="10" style="ime-mode:active;" placeholder="한글 10글자 이내"></td>
                        </tr>
                        <tr>
                            <td>시작일<a class="star">*</a></td>
                            <td><input type="date" v-model="requestBody.prjSTDT" id="prjSTDT"></td>
                        </tr> 
                        <tr>
                            <td>종료일<a class="star">*</a></td>
                            <td><input type="date" v-model="requestBody.prjEDDT" id="prjEDDT"></td>
                        </tr> 
                        <tr>
                            <td>고객사명</td>
                            <td>
                                <select name="cusCD" id="cusCD" v-model="requestBody.cusCD">
                                    <option value="0">선택</option>
                                    <option v-for="item in cusCDs" :key="item.id" :value="item.dtCD">{{ item.dtCDNM }}</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>필요기술<a class="star">*</a></td>
                            <td colspan="3">
                                <div class="skills">
                                    <template v-for="item in skCDs" :key="item.dtCDNM">
                                        <input type="checkbox" class="skill" :id="item.dtCD" :value="item.dtCD" v-model="requestBody.skillList">
                                        <label :for="item.dtCD">{{ item.dtCDNM }} </label>
                                    </template>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>비고</td>
                            <td><textarea v-model="requestBody.prjNote" id="prjNote"></textarea></td>
                        </tr>
                    </table>
                    {{ requestBody }}
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
                prjNm: '',
                prjSTDT: '',
                prjEDDT: '',
                cusCD: '0',
                skillList: [],
                prjNote: '',
            },
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
        },

        cusCDs: function () {
		const arr = this.$codeList.data
		const cusCDs = arr.filter(item => item.mstCD == "CU01")
		return cusCDs
		},
    },

    methods: {
        daumMap: function() {
            new window.daum.Postcode({
                oncomplete: (data) => {
                    this.requestBody.usrZIP = data.zonecode
                    this.requestBody.usrRAD = data.roadAddress
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
            axios.post('http://localhost:8080/addProject', this.requestBody)
            .then(function (response) {
                if(response.data == 1){
                    alert("등록되었습니다.")
                } else {
                    alert("잘못된 정보입니다.")
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
    },
};
</script>
  
<style scoped>
</style>
  