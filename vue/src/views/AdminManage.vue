<template>
  <div>
    <div id = "head">
      <div id = "leftTitle">
        <h1 class="display-3  title">Distributed Electricity Transaction Platform</h1>
      </div>
      <div id = "rightInfo">
        <button id = "logout" class="btn btn-primary btn-neutral" @click="logOut">Log Out</button>
        <p>Welcome: {{this.adminName | nullValue}}</p>
      </div>
    </div>
    <div class="container">
      <div>
        <button id = "add" class="btn btn-primary ni ni-fat-add" @click="addDShow = true">Add</button>
        <div>
          <modal :show.sync="addDShow"
              body-classes="p-0"
              modal-classes="modal-dialog-centered modal-sm">
            <card type="secondary" shadow
                  header-classes="bg-white pb-5"
                  body-classes="px-lg-5 py-lg-5"
                  class="border-0">
                <template>
                    <div class="text-center text-muted mb-4">
                        <h3>Add New User</h3>
                    </div>
                    <form role="form">
                      <base-input alternative
                                  v-model="accountInfo.name"
                                  placeholder="Name"
                                  addon-left-icon="ni ni-sound-wave" :required="true">
                      </base-input>
                      <base-input alternative
                                  type="number"
                                  v-model="accountInfo.elecAmount"
                                  placeholder="Amount"
                                  addon-left-icon="ni ni-sound-wave" :required="true">
                      </base-input>
                      <base-input alternative
                                  type="number"
                                  v-model="accountInfo.balance"
                                  placeholder="Balance"
                                  addon-left-icon="ni ni-sound-wave" :required="true">
                      </base-input>
                      <base-radio name="radio_user" class="mb-3" v-model="radio.radio1">
                          User
                      </base-radio>
                      <base-radio name="radio_admin" class="mb-3" v-model="radio.radio1">
                          Admin
                      </base-radio>
                      <div class="text-center">
                          <base-button type="primary" class="my-4" 
                          @click="confirmDShow = true; 
                            actionName = true;
                            accountInfo.name = accountInfo.name.slice(0,1).toUpperCase() + accountInfo.name.slice(1);
                            accountInfo.password = accountInfo.name + `pwd`; 
                            accountInfo.adminID = adminName; 
                            accountInfo.identity = (radio.radio1 === 'radio_user')?'User':'Admin' ">
                          Add
                        </base-button>
                          <base-button type="primary" class="my-4" @click="addDShow = false; clearAllInfo()">Close</base-button>
                      </div>
                    </form>
                </template>
            </card>
          </modal>
        </div>
      </div>
      <div id = "table">
        <div v-if="userList.length > 0">
          <table class="table">
            <thead>
                <tr>
                    <th class="text-center">#</th>
                    <th class="text-center">Account ID</th>
                    <th class="text-center">Electricity Amount</th>
                    <th class="text-center">Balance</th>
                    <th class="text-center">Identity</th>
                    <th class="text-center">Actions</th>
                </tr>
            </thead>
            <tbody :key="index" v-for="(user, index) in userList">
                <tr>
                    <td class="text-center">{{index+1}}</td>
                    <td class="text-center">{{(user.accountId === adminName)?user.accountId + ' (ME)':user.accountId}}</td>
                    <td class="text-center">{{(user.identity === 'User')?numFilter(user.elecAmount):'/'}}</td>
                    <td class="text-center">{{(user.identity === 'User')?numFilter(user.balance):'/'}}</td>
                    <td class="text-center">{{user.identity}}</td>
                    <td class="td-actions text-center">
                      <button type="button" rel="tooltip" class="btn btn-info btn-icon ni ni-archive-2"
                        :disabled="user.accountId === 'Admin'"
                        @click="actionName = false;
                          updateDShow = true;
                          accountInfo.name = user.accountId;
                          accountInfo.elecAmount = user.elecAmount;
                          accountInfo.balance = user.balance;
                          radio.radio1= (user.identity === 'User')?'radio_user':'radio_admin'">
                        Update
                      </button>
                      <button type="button" rel="tooltip" class="btn btn-success btn-icon ni ni-fat-remove" 
                      @click="accountInfo.name = user.accountId; removeDShow = true" :disabled="user.accountId === 'Admin'">
                        Remove
                      </button>
                    </td>
                </tr>
            </tbody>
          </table>
          <div>
            <modal :show.sync="removeDShow">
              <h5 slot="header" class="modal-title" id="modal-title-default">Remove User Confirm</h5>
              <p>Are you sure to remove user:</p>
              <h6>{{accountInfo.name}}</h6>
              <p>?</p>
              <template slot="footer">
                  <base-button type="primary" @click="removeUser()">Confirm</base-button>
                  <base-button type="link" class="ml-auto" @click="removeDShow = false; clearAllInfo()">Cancel
                  </base-button>
              </template>
            </modal>
            <modal :show.sync="updateDShow"
                body-classes="p-0"
                modal-classes="modal-dialog-centered modal-sm">
              <card type="secondary" shadow
                    header-classes="bg-white pb-5"
                    body-classes="px-lg-5 py-lg-5"
                    class="border-0">
                  <template>
                      <div class="text-center text-muted mb-4">
                          <h3>Update User</h3>
                      </div>
                      <form role="form">
                        <base-input alternative
                                    v-model="accountInfo.name"
                                    placeholder="Name"
                                    value="accountInfo.name"
                                    addon-left-icon="ni ni-sound-wave" :required="true" disabled>
                        </base-input>
                        <base-input alternative
                                    type="number"
                                    v-model="accountInfo.elecAmount"
                                    placeholder="Amount"
                                    value="accountInfo.elecAmount"
                                    addon-left-icon="ni ni-sound-wave" :required="true">
                        </base-input>
                        <base-input alternative
                                    type="number"
                                    v-model="accountInfo.balance"
                                    placeholder="Balance"
                                    value="accountInfo.balance"
                                    addon-left-icon="ni ni-sound-wave" :required="true">
                        </base-input>
                        <base-radio name="radio_user" class="mb-3" v-model="radio.radio2">
                            User
                        </base-radio>
                        <base-radio name="radio_admin" class="mb-3" v-model="radio.radio2">
                            Admin
                        </base-radio>
                        <div class="text-center">
                            <base-button type="primary" class="my-4" 
                            @click="confirmDShow = true; 
                              actionName = false;
                              accountInfo.adminID = adminName; 
                              accountInfo.identity = (radio.radio2 === 'radio_user')?'User':'Admin' ">
                            Update
                          </base-button>
                            <base-button type="primary" class="my-4" @click="updateDShow = false; clearAllInfo()">Close</base-button>
                        </div>
                      </form>
                  </template>
              </card>
            </modal>
          </div>
        </div>
        <div v-else>
          <h5>No other user.</h5>
        </div>
      </div>
      <div>
        <modal :show.sync="confirmDShow">
            <h6 slot="header" class="modal-title" id="modal-title-default">{{(actionName)?"Add New User Confirm":"Update User Confirm"}}</h6>
            <p>Username:           {{accountInfo.name}}</p>
            <p>Electricity Amount: {{accountInfo.elecAmount}}</p>
            <p>Balance:            {{accountInfo.balance}}</p>
            <p>Identity:           {{accountInfo.identity}}</p>
            <p v-show="actionName">Admin ID:           {{accountInfo.adminID}}</p>
            <template slot="footer">
                <base-button type="primary" @click="userOperation()">Confirm</base-button>
                <base-button type="link" class="ml-auto" @click="confirmDShow = false">Cancel</base-button>
            </template>
          </modal>
      </div>
    </div>
    
  </div>
  
</template>

<script>
  import Tabs from "@/components/Tabs/Tabs.vue";
  import TabPane from "@/components/Tabs/TabPane.vue";
  import Modal from "@/components/Modal.vue";
  import Store from "../Store.js"
  export default {
    name: "home",
    components: {
      Tabs,
      TabPane,
      Modal
    },
    data() {
      return {
          addDShow: false,    //if activate dialog
          updateDShow: false,
          removeDShow: false,
          confirmDShow: false,
          actionName: Boolean,
          adminName: Store.getItem("dis-elec-tran-name"),
          userIdentity: Store.getItem("dis-elec-tran-identity"),
          accountInfo: {
            name: null,
            elecAmount: null,
            balance: null,
            password: null,
            identity: null,
            adminID: this.adminName
          },
          userList: [],
          radio:{
            radio1: "radio_user",
            radio2: "radio_user"
          }
        }
    },
    created() {
      if(! Store.getItem("dis-elec-tran-name")){
          this.$router.push("/")
        }
      this.getAllUser();
    },
    filters: {
      nullValue(str) {
        return str || "Null";
      }
    },
    methods: {
      numFilter(value){
        value = parseFloat(value).toFixed(2)
        return value
      },
      logOut(){
        if(Store.getItem("dis-elec-tran-name") != null){
          window.localStorage.removeItem("dis-elec-tran-name")
          window.localStorage.removeItem("dis-elec-tran-identity")
        }
        
        this.$router.push("/")
      },
      async getAllUser() {
        await this.$axios({
          method: "get",
          url: `/transaction/queryall/${"all"}/${this.adminName}`
        }).then((resp) => {
          console.log(resp)
          console.log(resp.data.result)
          this.userList = resp.data.result
        })
      },
      userOperation() {
        let u = null
        if(this.actionName){
          // add
          u = `/userControl/add`
        }else{
          //update
          u = `/userControl/update`
        }
        this.$axios({
          method: "post",
          url: u,
          params: {
            name: this.accountInfo.name,
            elecAmount: this.accountInfo.elecAmount,
            balance: this.accountInfo.balance,
            password: this.action ? this.accountInfo.password: null,
            identity: this.accountInfo.identity,
            adminID: this.accountInfo.adminID
          }
        }).then((resp) => {
          console.log(resp)
          if(resp.status == 500 || resp.data.code == 500) {
            alert("Please check your internet and try again.");
            return;
          }
          if(resp.data.code == 200) {
            this.clearAllInfo()
            this.getAllUser()
            this.confirmDShow = false;
            if(this.actionName){
              // add
              this.addDShow = false;
            }else{
              //update
              this.updateDShow = false;
            }
            alert("Success");
          }else {
            alert("Some error occurs. Pleas try again.")
          }
        })
      },
      removeUser(){
        this.$axios({
          method: "post",
          url: `/userControl/delete`,
          params: {
            name: this.accountInfo.name
          }
        }).then((resp) => {
          console.log(resp)
          if(resp.status == 500 || resp.data.code == 500) {
            alert("Please check your internet and try again.");
            return;
          }
          if(resp.data.code == 200) {
            this.clearAllInfo()
            this.getAllUser()
            this.removeDShow = false;
            alert("Success");
          }else {
            alert("Some error occurs. Pleas try again.")
          }
        })
      },
      clearAllInfo(){
        this.accountInfo.name = null;
        this.accountInfo.elecAmount = null;
        this.accountInfo.balance = null;
        this.accountInfo.password = null;
        this.accountInfo.identity = null;
      }
    }
  };
</script>

<style>
  #head{
    display: flex;
    justify-content: space-between;
    height: fit-content;
  }
  #leftTitle{
    width: 70%;
    display: flex;
    justify-content: center;
  }
  #rightInfo{
    margin-top: 2%;
    margin-right: 70px;
    width: 30%;
    float: right;
    padding-left: 8%;
    text-align: right;
    vertical-align: auto;
  }
  .title{
    margin-top: 3%;
    margin-left: 70px;
    margin-bottom: 50px;
    color: #6A5ACD;
  }
  #logout{
    margin-right: -8px;
    margin-bottom: 10px;
  }
  .container{
    min-width: 85%;
  }
  #table{
    margin-top: 15px;
  }
</style>