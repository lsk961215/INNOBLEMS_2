<template>
    <div class="aside">
        <a v-for="(item, index) in this.AD" :key="index" :href="item.dtCDURL">{{ item.dtCDNM }}</a>
        <a v-for="(item, index) in this.EM" :key="index" :href="item.dtCDURL">{{ item.dtCDNM }}</a>
    </div>
</template>

<script>
export default {
    name: 'asideMenu',

	data () {
        return {
            AD: {

            },

            EM: {

            }
        }
    },

    created() {
        this.AD = this.$codeList.data.filter(item => item.mstCD == "AD01")
        this.EM = this.$codeList.data.filter(item => item.mstCD == "EM01")
    },
}
</script>

<style>
    .aside {
        min-width: 130px;
        background-color: white;
        
        margin: 50px;
            
        display: flex;
        flex-direction: column;
        
        height: inherit;
        
        min-height: 50vh;
        border: 2px solid lightgrey;
        text-align: center;
        padding: 1%
    }

    aside a{
	text-decoration: none;
	color: black;
	
	font-size: 110%;
	font-weight: bold;
	
	margin-bottom: 10%;
	
    }
    aside a:hover {
        color: grey;
    }
</style>