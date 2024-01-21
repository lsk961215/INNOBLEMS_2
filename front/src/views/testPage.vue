<template>
  <div>
    <h1>테스트페이지</h1>
  </div>
</template>
  
<script>
  import { defineComponent } from "vue";

  export default defineComponent({
  name: "codeList",
  mounted() {
    console.log(this.$codeList.data); // bar
  }
  });
</script>
  
<style scoped>
</style>
  