<template>
    <div>
        <p>Error</p>
        <button @click="click">fire!!!!</button>
    </div>
</template>
<script>
export default{
    methods:{
        click(){
            this.$router.push('home')
        }
    }
}
</script>