import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";
import AdminManage from "./views/AdminManage.vue";
import Login from "./views/Login.vue";

Vue.use(Router);

export default new Router({
  linkExactActiveClass: "active",
  routes: [
    {
      path: "/",
      name: "Login",
      components: {
        default: Login
      }
    },{
      path: "/home",
      name: "Home",
      components: {
        default: Home,
      }
    },{
      path: "/adminmanage",
      name: "AdminManage",
      components: {
        default: AdminManage,
      }
    },
    {
      path: "/login",
      name: "login",
      components: {
        default: Login
      }
    }
  ],
  scrollBehavior: to => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  }
});
