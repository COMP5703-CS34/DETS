<template>
    <section class="section section-shaped section-lg my-0">
        <div class="shape shape-style-1 bg-gradient-default">
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
        </div>
        <div class="container pt-lg-md">
            <div class="row justify-content-center">
                <div class="col-lg-5">
                    <card type="secondary" shadow
                          header-classes="bg-white pb-5"
                          body-classes="px-lg-5 py-lg-5"
                          class="border-0">
                        <template>
                            <div class="text-center mb-4">
                                <h3>Welcome!</h3>
                            </div>
                            <form role="form">
                                <base-input alternative
                                            class="mb-3"
                                            v-model="userInfo.name"
                                            placeholder="Username"
                                            addon-left-icon="ni ni-circle-08">
                                </base-input>
                                <base-input alternative
                                            type="password"
                                            v-model="userInfo.InputPwd"
                                            placeholder="Password"
                                            addon-left-icon="ni ni-lock-circle-open">
                                </base-input>
                                <div class="text-center">
                                    <base-button type="primary" class="my-4" @click="signIn">Sign In</base-button>
                                </div>
                            </form>
                        </template>
                    </card>
                </div>
            </div>
        </div>
    </section>
</template>
<script>
export default {
    data(){
        return{
            userInfo:{
                name: null,
                InputPwd: null
            }
        }
    },
    created(){
        this.judge();
    },
    methods:{
        judge(){
            if(localStorage.getItem("name") != null){
                window.localStorage.clear()
            }
        },
        signIn(){
            this.$axios({
                method: "post",
                url: `/login`,
                params:{
                    name: this.userInfo.name,
                    InputPwd: this.userInfo.InputPwd
                }
            }).then((resp) => {
                console.log(resp.data.result)
                if(resp.data.result){
                    localStorage.setItem("name", this.userInfo.name)
                    console.log(localStorage.getItem("name"))
                    this.$router.push("/")
                }else
                    alert("Username or password error!")
            })
        }
    }
};
</script>
<style>
</style>
