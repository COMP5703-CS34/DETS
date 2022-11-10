<template>
  <div>
    <!--Head Section-->
    <div id="head">
      <div id="leftTitle">
        <h1 class="display-3  title">Distributed Electricity Transaction Platform</h1>
      </div>
      <!--User Infomation Display-->
      <div id="rightInfo">
        <button id="logout" class="btn btn-primary btn-neutral" @click="logOut">Log Out</button>
        <p>Account ID: {{this.userInfo.accountId | nullValue}}</p>
        <p>Electricity Amount: {{numFilter(this.userInfo.elecAmount) | nullValue}}</p>
        <p>Balance: {{numFilter(this.userInfo.balance ) | nullValue}}</p>
      </div>
    </div>
    <!--Body Section-->
    <div>
      <!--Tabs on the left side of the page.-->
      <tabs fill class="container d-flex" @tabSwitch="getTabIndex($event)" >
        <card shadow style="width: 107%">
          <!--Transaction: Show users that is able to transact with. And make buy/sell request.-->
          <tab-pane class="scrollbox" title="Transaction" name="Transaction">
            <div>
              <!--If there are available users-->
              <div v-if="userList.length > 0">
                <table class="table">
                  <thead>
                      <tr>
                          <th class="text-center">#</th>
                          <th class="text-center">Account ID</th>
                          <th class="text-center">Electricity Amount</th>
                          <th class="text-center">Actions</th>
                      </tr>
                  </thead>
                  <tbody :key="userIndex" v-for="(user, userIndex) in userList">
                    <tr>
                      <td class="text-center">{{userIndex+1}}</td>
                      <td class="text-center">{{user.accountId}}</td>
                      <td class="text-center">{{numFilter(user.elecAmount)}}</td>
                      <td class="td-actions text-center">
                        <div v-if="havePending">
                          <!-- when user have have pending transaction,can't operate a new transaction -->
                          <span>You have pending transaction,can't operate a new transaction</span>
                        </div>
                        <!--Buy / Sell request making.-->
                        <div v-else>
                          <button type="button" rel="tooltip" class="btn btn-info btn-icon " 
                            @click="transactionActionName = true; checkInfo = user.elecAmount; transDialog(user.accountId)">
                          BUY
                        </button>
                        <button type="button" rel="tooltip" class="btn btn-success btn-icon " 
                            @click="transactionActionName = false; transDialog(user.accountId)">
                          SELL
                        </button>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <!--No available user.-->
              <div v-else>
                <h5>No other available user.</h5>
              </div>
            </div>
            <div>
              <!--Request infomation input dialog.-->
              <modal :show.sync="transDShow" body-classes="p-0" modal-classes="modal-dialog-centered modal-sm">
                <card type="secondary" shadow header-classes="bg-white pb-5" body-classes="px-lg-5 py-lg-5"
                  class="border-0">
                  <template>
                    <div class="text-center text-muted mb-4">
                      <h3>Transaction</h3>
                    </div>
                    <form role="form">
                      <span id="errorMessage" v-show = "! amountValid">{{amountError.message}}</span>
                      <base-input alternative type="number" v-model="transactionInfo.elecAmount" placeholder="Amount" v-on:input="amountMonitor"
                        addon-left-icon="ni ni-sound-wave">
                      </base-input>
                      <span id="errorMessage" v-show = "! priceValid">{{priceError.message}}</span>
                      <base-input alternative type="number" v-model="transactionInfo.elecPrice" placeholder="Price" v-on:input="priceMonitor"
                        addon-left-icon="ni ni-money-coins">
                      </base-input>
                      <div class="text-center">
                        <base-button type="primary" class="my-4" @click="transDShow = false; confirmDShow = true" 
                        :disabled="(!amountValid)&&(!priceValid)">OK</base-button>
                        <base-button type="primary" class="my-4" @click="transDShow = false; clearAllInfo()">Close
                        </base-button>
                      </div>
                    </form>
                  </template>
                </card>
              </modal>
              <!--Request infomation confirm dialog.-->
              <modal :show.sync="confirmDShow">
                <h6 slot="header" class="modal-title" id="modal-title-default">Transaction Confirm</h6>

                <p>Current User: {{this.queryName}}</p>
                <p v-show="this.transactionActionName">Buy from: {{this.transactionInfo.fromAccount}}</p>
                <p v-show="! this.transactionActionName">Sell to: {{this.transactionInfo.toAccount}}</p>
                <p>Electricity Amount: {{this.transactionInfo.elecAmount}}</p>
                <p>Electricity Price: {{this.transactionInfo.elecPrice}}</p>

                <template slot="footer">
                  <base-button type="primary" @click="loadingShow = true;TransactionRequest()">Confirm</base-button>
                  <base-button type="link" class="ml-auto" @click="confirmDShow = false; clearAllInfo()">Cancel
                  </base-button>
                </template>
              </modal>
            </div>
          </tab-pane>

          <!--History on blockchain.-->
          <tab-pane class="scrollbox" title="History">
            <div>
              <p>Please note: The history includes all changes on your account including account adding & updating as well as completed transactions.</p>
              <div v-if="historyList.length > 0">
                <table class="table">
                  <thead>
                      <tr>
                          <th class="text-center">Datetime</th>
                          <th class="text-center">Transaction ID</th>
                          <th class="text-center">Electricity Amount</th>
                          <th class="text-center">Remain Balance</th>
                      </tr>
                  </thead>
                  <tbody :key="item.transactionId" v-for="item in historyList">
                      <tr>
                          <td class="text-center">{{item.timestamp}}</td>
                          <td class="text-center" style="word-break:break-all; width:35%;">{{item.transactionId}}</td>
                          <td class="text-center">{{numFilter(item.elecAmount)}}</td>
                          <td class="text-center">{{numFilter(item.balance)}}</td>
                      </tr>
                  </tbody>
                </table>
              </div>
              <div v-else>
                <h5>No transaction history.</h5>
              </div>
            </div>
          </tab-pane>

          <!-- Pending Transaction tab -->
          <tab-pane class="scrollbox" title="Pending">
              <div>
              <!-- bargaining dialog -->
              <modal :show.sync="bargainingShow" body-classes="p-0" modal-classes="modal-dialog-centered modal-sm">
                <card type="secondary" shadow header-classes="bg-white pb-5" body-classes="px-lg-5 py-lg-5"
                  class="border-0">
                  <template>
                    <div class="text-center text-muted mb-4">
                      <h3>Bargaining</h3>
                    </div>
                    <form role="form">
                      <base-input alternative type="number" v-model="bargainingPrice" placeholder="Price"
                        addon-left-icon="ni ni-money-coins">
                      </base-input>
                      <div class="text-center">
                        <base-button type="primary" class="my-4" @click="bargainingShow = false;loadingShow = true;giveNewPrice()">OK
                        </base-button>
                        <base-button type="primary" class="my-4" @click="bargainingShow = false; clearAllInfo()">Close
                        </base-button>
                      </div>
                    </form>
                  </template>
                </card>
              </modal>
              <!--Transaction confirm / reject dialog.-->
              <modal :show.sync="transactionConfirmShow">
                <h6 v-show="requestActionName" slot="header" class="modal-title" id="modal-title-default">Trasaction Accept Confirm</h6>
                <h6 v-show="! requestActionName" slot="header" class="modal-title" id="modal-title-default">Trasaction Reject Confirm</h6>

                <p>Current User: {{this.queryName}}</p>
                <div v-if="this.transactionItem">
                  <p v-show="this.transactionItem.to == this.queryName">Buy from: {{this.transactionItem.from}}</p>
                  <p v-show="this.transactionItem.from == this.queryName">Sell to: {{this.transactionItem.to}}</p>
                  <p>Electricity Amount: {{numFilter(this.transactionItem.amount)}}</p>
                  <p>Electricity Price: {{numFilter(this.transactionItem.price)}}</p>
                </div>

                <template slot="footer">
                  <base-button v-show="requestActionName" type="primary" @click="loadingShow = true; transactionConfirmShow = false; confirmTransaction(transactionItem)">Confirm</base-button>
                  <base-button v-show="!requestActionName" type="primary" @click="loadingShow = true; transactionConfirmShow = false; rejectTransaction(transactionItem)">Reject</base-button>
                  <base-button type="link" class="ml-auto" @click="transactionConfirmShow = false; clearAllInfo()">Cancel
                  </base-button>
                </template>
              </modal>
            </div>
            <!--Pending request processing.-->
            <div>
              <p>Please note: Buy = Buy from me; Sell = Sell to me.</p>
              <!--If there is any requests including history ones.-->
              <div v-if="transactionRequestList.length > 0">
                <table class="table">
                  <thead>
                    <tr>
                      <th class="text-center">#</th>
                      <th class="text-center">Creat At</th>
                      <th class="text-center">Last Update</th>
                      <th class="text-center">With</th>
                      <th class="text-center">Request</th>
                      <th class="text-center">Electricity Amount</th>
                      <th class="text-center">Electricity Price</th>
                      <th class="text-center">Status</th>
                      <th class="text-center">Actions</th>
                    </tr>
                  </thead>
                  <tbody :key="index" v-for="(item, index) in transactionRequestList">
                    <tr :class="{'highlight': (item.status == 0)}">
                      <td class="text-center">{{index+1}}</td>
                      <td class="text-center" style="word-break:break-all; width:10%;">{{item.createTime | timeFormat}}</td>
                      <td class="text-center" style="word-break:break-all; width:10%;">{{item.updateTime | timeFormat}}</td>
                      <td class="text-center">{{(item.from == queryName) ? item.to : item.from}}</td>
                      <td class="text-center">{{(item.from == queryName) ? "Buy" : "Sell"}}</td>
                      <td class="text-center">{{numFilter(item.amount)}}</td>
                      <td class="text-center">{{numFilter(item.price)}}</td>
                      <td class="text-center">
                        {{item.status == 0 ? "Pending" : item.status == 1 ? "Accepted" : "Rejected/Cancelled"}}
                      </td>
                      <td class="td-actions text-center">
                        <!-- show BARGAINING and CONFIRM button only when current user is bargainingUser -->
                        <button v-show="item.status == 0 && item.bargainingUser == userInfo.accountId" type="button" rel="tooltip"
                          class="btn btn-info btn-sm btn-icon " @click="transactionActionName = true; bargaining(item);">
                          BARGAINING
                        </button>
                        <button v-show="item.status == 0 && item.bargainingUser == userInfo.accountId" type="button" rel="tooltip"
                          class="btn btn-success btn-sm btn-icon " @click="transactionItem=item; transactionConfirmShow = true; requestActionName = true">
                          CONFIRM
                        </button>
                        <button v-show="item.status == 0 && item.bargainingUser == userInfo.accountId" type="button" rel="tooltip" class="btn btn-warning btn-sm btn-icon "
                          @click="transactionItem=item; transactionConfirmShow = true; requestActionName = false">
                          REJECT
                        </button>
                        <button v-show="item.status == 0 && item.bargainingUser != userInfo.accountId" type="button" rel="tooltip" class="btn btn-warning btn-sm btn-icon "
                          @click="transactionItem=item; transactionConfirmShow = true; requestActionName = false">
                          CANCEL
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <!--No pending to show.-->
              <div v-else>
                <h5>No pending transaction.</h5>
              </div>
            </div>
          </tab-pane>

          <!-- Set new password -->
          <tab-pane title="Setting">
            <div>
              <form role="form">
                <span id="errorMessage" v-show = "! pwdValid">The two passwords are not the same.</span>
                <base-input alternative type="password" v-model="newPwd.first" v-on:input="newPwdFirstMonitor"
                    placeholder="New Password">
                </base-input>
                <base-input alternative type="password" v-model="newPwd.check" v-on:input="newPwdCheckMonitor"
                    placeholder="Confirm Password">
                </base-input>
                <div class="text-center">
                    <base-button type="primary" class="my-4" @click="loadingShow = true;setNewPassword()" 
                    :disabled="!pwdValid">Set new Password</base-button>
                </div>
              </form>
            </div>
          </tab-pane>
        </card>
      </tabs>
    </div>

    <!--Loading dialog.-->
    <div>
      <modal :show.sync="loadingShow" body-classes="p-0" modal-classes="modal-dialog-centered modal-sm">
        <card type="secondary" shadow header-classes="bg-white pb-5" body-classes="px-lg-5 py-lg-5"
          class="border-0">
          <template>
              <h5>Processing... </h5>
              <h5>Please wait a minute.</h5>
          </template>
        </card>
      </modal>
    </div>
  </div>

</template>

<script>
import Tabs from "@/components/Tabs/Tabs.vue";
import TabPane from "@/components/Tabs/TabPane.vue";
import Modal from "@/components/Modal.vue";
import Store from "../Store.js"
import { userInfo } from "os";
export default {
  name: "home",
  components: {
    Tabs,
    TabPane,
    Modal
  },
  data () {
    return {
      transDShow: false,                    //if activate request making dialog
      confirmDShow: false,                  //double check dialog for request making
      bargainingShow: false,                //bargin dialog
      transactionConfirmShow: false,        //double confirm dialog for request accept/reject
      loadingShow: false,                   //loading dialog
      transactionActionName: Boolean,       //Request making: buy true, sell false
      requestActionName: Boolean,           //accept true, reject false      
      userInfo: {     
        accountId: null,
        elecAmount: null,
        balance: null
      },
      queryName: Store.getItem("dis-elec-tran-name"),
      userIdentity: Store.getItem("dis-elec-tran-identity"),
      transactionInfo: {
        toAccount: null,
        fromAccount: null,
        elecAmount: null,
        elecPrice: 1
      },
      checkInfo: null,
      havePending: false,
      bargainingPrice: null,
      bargainingId: null,
      userList: [],
      historyList: [],
      bargainingUser: null,
      transactionRequestList: [],
      amountError: {
        valid: null,
        message: null
      },
      priceError: {
        valid: null,
        message: null
      },
      newPwd: {
        first: null,
        check: null,
        valid: true
      },
      transactionItem: null
    }
  },
  created () {
    // check if the user has login and not logged out
    if(Store.getItem("dis-elec-tran-name") == null){
        this.$router.push("/")
    } else {
      this.getUserInfo();
      this.getAllUser();
      this.getTransactionRequest();
    }
  },
  filters: {
    nullValue (str) {
      return str || "Null";
    },
    timeFormat(value){
      var date = new Date(parseInt(value * 1000));
      var tt = [date.getFullYear(), (Array(2).join("0") + (date.getMonth() + 1)).slice(-2), (Array(2).join("0") + date.getDate()).slice(-2)].join('-')  
      + " " 
      + [(Array(2).join("0") + date.getHours()).slice(-2), (Array(2).join("0") + date.getMinutes()).slice(-2), (Array(2).join("0") + date.getSeconds()).slice(-2)].join(':');
      return tt;
    }
  },
  methods: {
    numFilter(value){       //Display two decimal places
      value = parseFloat(value).toFixed(2)
      return value
    },
    //dynamically judge window size and set the width of review section
    searchFormHeight() {
      let h = window.innerHeight;
      var scrollList = document.getElementsByClassName("scrollbox");
      for(var i = 0; i < scrollList.length; i++){
        scrollList[i].setAttribute("style", `height: ${h*0.65}px`);
      }
    },
    logOut(){
      if(Store.getItem("dis-elec-tran-name") != null){
        Store.removeItem("dis-elec-tran-name")
        Store.removeItem("dis-elec-tran-identity")
      }
      this.$router.push("/")
    },
    async getUserInfo () {
      await this.$axios({
        method: "get",
        url: `/userInfo/${this.queryName}`
      }).then((resp) => {
        console.log(resp)
        if(resp.data.code == 200 && resp.data.result != null) {
          this.userInfo = resp.data.result;
        }
        else {
          this.$router.push("/")
          alert("Error. Not able to get user infomation.")
        }
      }).catch((error) => {
        console.log(error);
        alert("Please check your network connection and try again.");
      });
    },
    // create new transaction request
    TransactionRequest () {
      this.$axios({
        method: "post",
        url: `/add`,
        params: {
          from: this.transactionInfo.fromAccount,
          to: this.transactionInfo.toAccount,
          amount: this.transactionInfo.elecAmount,
          price: this.transactionInfo.elecPrice,
          createTime: (new Date().getTime() / 1000) | 0,
          updateTime: (new Date().getTime() / 1000) | 0,
          // change bargaining user to peer user
          bargainingUser: this.userInfo.accountId == this.transactionInfo.fromAccount ? this.transactionInfo.toAccount : this.transactionInfo.fromAccount,
          status: 0,
        }
      }).then((resp) => {
        console.log(resp)
        this.loadingShow = false;
        if(resp.data.code == 200) {
          this.clearAllInfo()
          this.getAllUser()
          this.getTransactionRequest();
          this.confirmDShow = false;
          alert("Success")
        } else {
          alert("Some error occurs. Pleas try again.")
        }
      }).catch((error) => {
        console.log(error);
        this.loadingShow = false;
        alert("Please check your network connection and try again.");
      });
    },
    //Bargin infomation update
    giveNewPrice () {
      this.$axios({
        method: "post",
        url: `/update`,
        params: {
          id: this.bargainingId,
          price: this.bargainingPrice,
          bargainingUser: this.bargainingUser,
          status: 0,
          updateTime: (new Date().getTime() / 1000) | 0
        }
      }).then((resp) => {
        console.log(resp)
        this.loadingShow = false;
        if(resp.data.code == 200) {
          this.clearAllInfo()
          this.getTransactionRequest();
          this.bargainingShow = false;
          alert("Success")
        } else {
          alert("Some error occurs. Pleas try again.")
        }
      }).catch((error) => {
        console.log(error);
        this.loadingShow = false;
        alert("Please check your network connection and try again.");
      });
    },
    // reject transaction, set status to 2
    rejectTransaction (transaction) {
      this.$axios({
        method: "post",
        url: `/update`,
        params: {
          id: transaction.id,
          price: transaction.price,
          bargainingUser: '',
          status: 2,
          updateTime: (new Date().getTime() / 1000) | 0
        }
      }).then((resp) => {
        console.log(resp)
        this.loadingShow = false;

        if(resp.data.code == 200) {
          this.clearAllInfo()
          this.getTransactionRequest();
          alert("Success")
        } else {
          alert("Some error occurs. Pleas try again.")
        }
      }).catch((error) => {
        console.log(error);
        this.loadingShow = false;
        alert("Please check your network connection and try again.");
      });
    },
    // confirm transaction
    confirmTransaction (transaction) {
      this.$axios({
        method: "post",
        url: `/transaction/transfer`,
        params: {
          fromAccount: transaction.from,
          toAccount: transaction.to,
          elecAmount: transaction.amount + '',
          elecPrice: transaction.price + '',
        }
      }).then((resp) => {
        console.log(resp)
        this.loadingShow = false;
        if(resp.data.code != 200) {
          alert("The transaction was unsuccessful. \nTo ensure that your other transactions can go smoothly, this request has been automatically closed.");
          this.rejectTransaction(transaction);
          return;
        }
        this.clearAllInfo()
        this.getUserInfo()
        if (resp.data.code == 200) {
          // transaction success
          this.$axios({
            method: "post",
            url: `/update`,
            params: {
              id: transaction.id,
              price: transaction.price,
              bargainingUser: '',
              status: 1,
              updateTime: (new Date().getTime() / 1000) | 0
            }
          }).then((resp) => {
            console.log(resp)
            this.loadingShow = false;
            if(resp.data.code == 200) {
              this.clearAllInfo()
              this.getUserInfo()
              this.getTransactionRequest();
              alert("Success")
            } else {
              alert("Some error occurs. Pleas try again.")
            }
          }).catch((error) => {
            console.log(error);
            this.loadingShow = false;
            alert("Please check your network connection and try again.");
          });
        }
      }).catch((error) => {
        console.log(error);
        this.loadingShow = false;
        alert("Please check your network connection and try again.\n If you have tried several times and you are sure your network is ok, please reject the transaction.");
      });
    },
    //get all available users that are able to do transaction
    async getAllUser () {
      await this.$axios({
        method: "get",
        url: `/transaction/queryall/${"useronly"}/${this.queryName}`
      }).then((resp) => {
        console.log(resp)
        if(resp.data.code == 200)
          this.userList = resp.data.result
        else
          alert("Some error occurs. Pleas try again.");
      }).catch(function (error) {
        console.log(error);
        alert("Please check your network connection and try again.");
      });
    },
    async getHistory () {
      await this.$axios({
        method: "get",
        url: `/History/${this.queryName}`
      }).then((resp) => {
        console.log(resp)

        if(resp.data.code == 200)
          // get history which is not marked deleted
          this.historyList = resp.data.result.filter((item) => {
              return item.isDeleted == false
          })   
        else
          alert("Some error occurs. Pleas try again.");
        return;
      }).catch(function (error) {
        console.log(error);
        alert("Please check your network connection and try again.");
      });
    },
    // get all transaction request
    async getTransactionRequest () {
      await this.$axios({
        method: "get",
        url: `/query/${this.queryName}`
      }).then((resp) => {
        console.log(resp)

        if(resp.data.code == 200) {
          this.transactionRequestList = resp.data.result
          // get pending transaction request which status is 0
          this.havePending = (this.transactionRequestList.filter((item) => {
            return item.status == 0
          }).length > 0)
        } else {
          alert("Some error occurs. Pleas try again.");
        }
      }).catch(function (error) {
        console.log(error);
        alert("Please check your network connection and try again.");
      });
    },
    //switch tabs
    getTabIndex (res) {
      switch (res) {
        case 0:
          this.newPwd.first = null;
          this.newPwd.check = null;
          this.newPwd.valid = true;
          this.getAllUser();
          this.getTransactionRequest();
          break;
        case 1:
          this.newPwd.first = null;
          this.newPwd.check = null;
          this.newPwd.valid = true;
          this.getHistory();
          break;
        case 2:
          this.newPwd.first = null;
          this.newPwd.check = null;
          this.newPwd.valid = true;
          this.getTransactionRequest();
          break;
        case 3:
          this.newPwd.first = null;
          this.newPwd.check = null;
          this.newPwd.valid = true;
          break;
      }
    },
    transDialog (transUser) {
      this.transDShow = true;
      if (this.transactionActionName) {
        //Buy
        this.transactionInfo.fromAccount = transUser;
        this.transactionInfo.toAccount = this.queryName;
      } else {
        //Sell
        this.transactionInfo.toAccount = transUser;
        this.transactionInfo.fromAccount = this.queryName;
      }
    },
    bargaining (transaction) {
      // show bargaining dialog and set bargaining id, bargaining user, bargaining price
      this.bargainingShow = true;
      this.bargainingId = transaction.id;
      this.bargainingPrice = transaction.price;
      this.bargainingUser = transaction.from == this.userInfo.accountId ? transaction.to : transaction.from;
    },
    clearAllInfo () {
      this.transactionInfo.fromAccount = null;
      this.transactionInfo.toAccount = null;
      this.transactionInfo.elecAmount = null;
      this.bargainingPrice = null;
      this.bargainingId = null;
    },
    setNewPassword(){
      this.$axios({
        method: "post",
        url: `/userControl/setPasswd`,
        params:{
          name: this.queryName,
          password: this.$md5(this.newPwd.first)
        }
      }).then((resp) => {
        console.log(resp)
        this.loadingShow = false;
        if(resp.data.code == 200) {
          this.newPwd.first = null;
          this.newPwd.check = null;
          this.newPwd.valid = true;
          alert("You have successfully set new password!");
        } else {
          alert("Some error occurs. Pleas try again.")
        }
      }).catch(function (error) {
        console.log(error);
        this.loadingShow = false;
        alert("Please check your network connection and try again.");
      });
    },
    //first password monitor
    newPwdFirstMonitor(res){
      if(this.newPwd.first != null && this.newPwd.check != null && this.newPwd.check != res) {
        this.$set(this.newPwd, "valid", false);
      } else if (res == "" && this.newPwd.check == "") {
        this.$set(this.newPwd, "valid", true);
      } else {
        this.$set(this.newPwd, "valid", true);
      }
    },
    //second password for double checking
    newPwdCheckMonitor(res){
      if(this.newPwd.first != null && this.newPwd.check != null && this.newPwd.first != res) {
        this.$set(this.newPwd, "valid", false);
      } else if (res == "" && this.newPwd.first == "") {
        this.$set(this.newPwd, "valid", true);
      } else {
        this.$set(this.newPwd, "valid", true);
      }
    },
    amountMonitor(res){
      if(res == null || res == ""){                 //empty input
        this.$set(this.amountError, "valid", false);
        this.$set(this.amountError, "message", "Input can not be empty.");
      } else if(res.toString().slice(0,1) == '-' || res < 0){       //negative input
        this.$set(this.amountError, "valid", false);
        this.$set(this.amountError, "message", "Please enter a positive number.");
      } else if (this.transactionActionName && res > this.checkInfo) {           //out of stock
        this.$set(this.amountError, "valid", false);
        this.$set(this.amountError,"message", "Not enough amount, max: " + this.checkInfo);
      } else if (!this.transactionActionName && res > this.userInfo.elecAmount) {
        this.$set(this.amountError, "valid", false);
        this.$set(this.amountError,"message", "Not enough amount, max: " + this.userInfo.elecAmount);
      } else if (this.transactionActionName && res * this.transactionInfo.elecPrice > this.userInfo.balance) {
        this.$set(this.amountError, "valid", false);
        this.$set(this.amountError,"message", "Not enough money, please note your balance.");
      } else {
        this.$set(this.amountError, "valid", true);
      }
    },
    priceMonitor(res){
      if(res == null || res == ""){                 //empty input
        this.$set(this.priceError, "valid", false);
        this.$set(this.priceError, "message", "Input can not be empty.");
      } else if(res.toString().slice(0,1) == '-' || res < 0){       //negative input or not number
        this.$set(this.priceError, "valid", false);
        this.$set(this.priceError, "message", "Please enter a positive number.");
      } else if (this.transactionActionName && res * this.transactionInfo.elecPrice > this.userInfo.balance) {           //out of stock
        this.$set(this.priceError, "valid", false);
        this.$set(this.priceError,"message", "Not enough money, please note your balance. ");
      }  else {
        this.$set(this.priceError, "valid", true);
      }
    }
  },
  computed: {
    pwdValid:function(){
      return this.newPwd.valid
    },
    amountValid:function(){
      return this.amountError.valid
    },
    priceValid:function(){
      return this.priceError.valid
    }
  },
  mounted(){
    // dynamic resize the review section
    this.searchFormHeight(); 
    window.onresize = () => {
      if(!this.timer){ // use timer to limit resize frequency
        this.timer = true;
        let that = this; 
        setTimeout(function(){
          that.searchFormHeight();
          that.timer = false;
        },400)
      }
    }
  },
  destroyed() {
		// unbind event when destory window
		window.onresize = null;
	}
};
</script>

<style>
#head {
  display: flex;
  justify-content: space-between;
  height: 20%;
}

#leftTitle {
  width: 70%;
  margin-top: 2%;
  display: flex;
  padding-right: -3%;
  justify-content: flex-start;
}

#rightInfo {
  margin-top: 2%;
  padding-right: 5%;
  width: 30%;
  float: right;
  text-align: right;
  vertical-align: auto;
}

.title {
  margin-top: 3%;
  margin-left: 70px;
  margin-bottom: 50px;
  color: #6A5ACD;
}

#logout {
  margin-right: -8px;
  margin-bottom: 10px;
}

.inputDiv {
  display: flex;
  justify-content: space-between;
  width: 40%;
}

.trans {
  width: 70%;
  float: right;
  justify-content: end;
}

.highlight {
  background-color: #FFCC99;
  font-weight: bolder;
}

.scrollbox {
  height: 637.5px;
  overflow-y: auto;
}
</style>
