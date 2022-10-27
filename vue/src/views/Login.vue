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
                    <card type="secondary" shadow header-classes="bg-white pb-5" body-classes="px-lg-5 py-lg-5"
                        class="border-0">
                        <template>
                            <div class="text-center mb-4">
                                <h3>Welcome!</h3>
                            </div>
                            <form role="form">
                                <span id="errorMessage" v-show = "!valid">{{error.message}}</span>
                                <base-input alternative class="mb-3" v-model="userInfo.name" v-on:input="inputMonitor"
                                    placeholder="Username" addon-left-icon="ni ni-circle-08" :required="true">
                                </base-input>
                                <base-input alternative type="password" v-model="userInfo.InputPwd"
                                    placeholder="Password" addon-left-icon="ni ni-lock-circle-open" :required="true">
                                </base-input>
                                <div class="text-center">
                                    <base-button type="primary" class="my-4" @click="signIn" 
                                    :disabled="!valid">Sign In</base-button>
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
import Store from "../Store.js"
export default {
    data () {
        return {
            userInfo: {
                name: null,
                InputPwd: null,
                identity: null
            },
            nameValid: {
                value: true
            },
            error: {
                message: null
            }
        }
    },
    methods: {
        signIn () {
            this.$axios({
                method: "post",
                url: `/login`,
                params: {
                    name: this.userInfo.name,
                    InputPwd: (this.userInfo.name === 'Admin') ? this.userInfo.InputPwd : this.$md5(this.userInfo.InputPwd)
                }
            }).then((resp) => {
                console.log(resp)
                if (resp.data.code == 200) {
                    let nameParams = {
                        key: 'dis-elec-tran-name',
                        value: this.userInfo.name,
                        validity: 259200000    //3days
                    }
                    let identityParams = {
                        key: 'dis-elec-tran-identity',
                        value: resp.data.result,
                        validity: 259200000    //3days
                    }
                    Store.setItem(nameParams)
                    Store.setItem(identityParams)
                    if (resp.data.result.toUpperCase() == "ADMIN") {
                        this.$router.push("/adminmanage")
                    } else {
                        this.$router.push("/home")
                    }
                } else
                    alert("Username or password error!");
                return;
            }).catch(function (error) {
                console.log(error);
                alert("Please check your internet and try again.");
            });
        },
        inputMonitor(res){
            if(res == null || res == ""){                 //empty input
                this.$set(this.nameValid, "value", false);
                this.$set(this.error, "message", "Input can not be empty.");
            } else {
                var c = res.charAt(0).charCodeAt()
                if(c < 65 || c > 90){
                    this.$set(this.nameValid, "value", false);
                    this.$set(this.error, "message", "The first letter must be a capital letter!");
                }else {
                    this.$set(this.nameValid, "value", true);
                    this.$set(this.error, "message", null);
                }
            }        
        }
    },
    computed: {
        valid:function(){
            return this.nameValid.value
        }
    }
};
</script>
<style>
    #errorMessage{
        color: red;
        margin-bottom: 10px;
    }
</style>
