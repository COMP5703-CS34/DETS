/*!

=========================================================
* Vue Argon Design System - v1.1.0
=========================================================

* Product Page: https://www.creative-tim.com/product/argon-design-system
* Copyright 2019 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/argon-design-system/blob/master/LICENSE.md)

* Coded by www.creative-tim.com

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import Argon from "./plugins/argon-kit";
import './registerServiceWorker'
import md5 from 'js-md5'

var axios = require('axios')
var default_port = 3000
axios.defaults.baseURL = `http://localhost:${default_port}/api`
var myVar=setInterval(function(){urlState()},700);

// Global registration
Vue.prototype.$axios = axios
Vue.prototype.$md5 = md5;

Vue.config.productionTip = false;
Vue.use(Argon);
new Vue({
  router,
  render: h => h(App)
}).$mount("#app");

function urlState(){
  if(default_port <= 3010) {
      axios({
      method: "get",
      url: `http://localhost:${default_port}/api/test`
    }).then((resp) => {
      console.log(resp)
      if(resp.status == 200) {
        axios.defaults.baseURL = resp.config.url.replace("/test", "")
        clearInterval(myVar);
        return;
      }
    })
    default_port ++;
  } else {
    clearInterval(myVar);
    alert("Some error occur in server connection. \nPlease contact technists.")
  }
  
}