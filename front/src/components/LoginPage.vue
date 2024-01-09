<template>
    <div class="login_form">
        <div>
            <p>
                <label for="username">username</label>
                <input type="text" id="name" name="name" required minlength="4" maxlength="10" size="10" v-model="username">
            </p>
            <p>
                <label for="password">password</label>
                <input type="password" id="password" name="password" required minlength="4" maxlength="10" size="10" v-model="password">
            </p>
            <input class="input-button" type="submit" @click="login">
        </div>
    </div>
</template>
<script>
export default{
    data:()=>({
        username:'',
        password: '',
    }),
    methods:{
        login(){
            const headers = {
                'Content-Type': 'application/json',
            };
            const body = JSON.stringify({username:this.username,password:this.password})
            console.log(body)
            fetch("http://localhost:8080/login", {method: "POST", headers: headers, body: body, mode: 'cors'},)
            .then(res => {
                if(res.status !== 200)throw new Error()
                this.$store.dispatch('saveToken', res.headers.get('x-auth-token'))
                return res.text()
            })
            .then(userId => {
                this.$store.dispatch('saveLoginUserId', userId)
                this.$router.push('/home')
            })
            .catch( e => {
                alert("ログインに失敗しました")
                console.log(e)
            })
        }
    }
}
</script>
<style scoped>
label {
    display: block;
    font: 1rem 'Fira Sans', sans-serif;
}
input {
    width: 30vw;
    border: 1px solid #333;
}
.login_form {
    display: flex;
    justify-content: center;
    padding-top: 5vh;
}
.input-button {
    background-color: darkgray;
}
</style>
