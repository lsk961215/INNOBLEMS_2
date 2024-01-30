<template>
    <div>테스트
        <VCalendar class="my-calendar"
            :initial-page="{ month: 2, year: 2024 }"
            :attributes="attributes"
        />
    </div>
    <button v-on:click="daumMap">주소찾기</button>
</template>

<script setup>
import axios from "axios"
import { ref } from 'vue';

const attributes = ref([
  {
    dot: 'green',
    dates: [
        {
            repeat: {
                every: 'day',
            },
        },
    ],
  },
  {
    dot: 'green',
    dates: [],
  },
  {
    dot: 'red',
    dates: [],
  },
  {
    dot: 'red',
    dates: [],
  },
]);
</script>

<script>

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

            date: {
                days: []
            }
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

        changeCalendar: function() {
        }
    },
};
</script>
  
<style>
.my-calendar .vc-weekday-1 {
  color: red;
}
.my-calendar .vc-weekday-7, .my-calendar .vc-week-7{
  color: #6366f1;
}
</style>
  