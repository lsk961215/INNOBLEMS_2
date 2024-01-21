<template>
    <button @click="get">코드리스트 가져오기</button>
    <p>{{ data }}</p>
</template>

<script>
import axios from "axios";

export default {
  name: "App",
  data() {
    return {
      data: [],
      id: 1,
    };
  },
  methods: {
    get() {
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
  },
};
</script>
  
<style scoped>
</style>
  