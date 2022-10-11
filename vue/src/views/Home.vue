<template>
  <div>
    <div id="head">
      <div id="leftTitle">
        <h1 class="display-3  title">Distributed Electricity Transaction Platform</h1>
      </div>
      <div id="rightInfo">
        <button id="logout" class="btn btn-primary btn-neutral" @click="logOut">Log Out</button>
        <p>Account ID: {{this.userInfo.accountId | nullValue}}</p>
        <p>Electricity Amount: {{this.userInfo.elecAmount | nullValue}}</p>
        <p>Balance: {{this.userInfo.balance | nullValue}}</p>
      </div>
    </div>
    <div>
      <tabs fill class="container d-flex" @tabSwitch="getTabIndex($event)">
        <card shadow>
          <tab-pane title="Transaction" name="Transaction">
            <div>
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
                  <tbody :key="index" v-for="(user, index) in userList">
                    <tr>
                      <td class="text-center">{{index+1}}</td>
                      <td class="text-center">{{user.accountId}}</td>
                      <td class="text-center">{{user.elecAmount}}</td>
                      <td class="td-actions text-center">
                        <div v-if="pendingTransactionRequestList.length > 0">
                          <span>You have pending transaction,can't operate a new transaction</span>
                        </div>
                        <div v-else>
                          <button type="button" rel="tooltip" class="btn btn-info btn-icon "
                            @click="actionName = true; transDialog(user.accountId)">
                            BUY
                          </button>
                          <button type="button" rel="tooltip" class="btn btn-success btn-icon "
                            @click="actionName = false; transDialog(user.accountId)">
                            SELL
                          </button>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div v-else>
                <h5>No other user.</h5>
              </div>
            </div>
            <div>
              <modal :show.sync="transDShow" body-classes="p-0" modal-classes="modal-dialog-centered modal-sm">
                <card type="secondary" shadow header-classes="bg-white pb-5" body-classes="px-lg-5 py-lg-5"
                  class="border-0">
                  <template>
                    <div class="text-center text-muted mb-4">
                      <h3>Transaction</h3>
                    </div>
                    <form role="form">
                      <base-input alternative type="number" v-model="transactionInfo.elecAmount" placeholder="Amount"
                        addon-left-icon="ni ni-sound-wave">
                      </base-input>
                      <base-input alternative type="number" v-model="transactionInfo.elecPrice" placeholder="Price"
                        addon-left-icon="ni ni-money-coins">
                      </base-input>
                      <div class="text-center">
                        <base-button type="primary" class="my-4" @click="transDShow = false; confirmDShow = true">OK
                        </base-button>
                        <base-button type="primary" class="my-4" @click="transDShow = false; clearAllInfo()">Close
                        </base-button>
                      </div>
                    </form>
                  </template>
                </card>
              </modal>
              <modal :show.sync="confirmDShow">
                <h6 slot="header" class="modal-title" id="modal-title-default">Transaction Confirm</h6>

                <p>Current User: {{this.queryName}}</p>
                <p v-show="this.actionName">Buy from: {{this.transactionInfo.fromAccount}}</p>
                <p v-show="! this.actionName">Sell to: {{this.transactionInfo.toAccount}}</p>
                <p>Electricity Amount: {{this.transactionInfo.elecAmount}}</p>
                <p>Electricity Price: {{this.transactionInfo.elecPrice}}</p>

                <template slot="footer">
                  <base-button type="primary" @click="TransactionRequest()">Confirm</base-button>
                  <base-button type="link" class="ml-auto" @click="confirmDShow = false; clearAllInfo()">Cancel
                  </base-button>
                </template>
              </modal>
            </div>
          </tab-pane>

          <tab-pane title="History">
            <div>
              <div v-if="historyList.length > 0">
                <table class="table">
                  <thead>
                    <tr>
                      <th class="text-center">#</th>
                      <th class="text-center">From</th>
                      <th class="text-center">To</th>
                      <th class="text-center">Electricity Amount</th>
                      <th class="text-center">Electricity Price</th>
                    </tr>
                  </thead>
                  <tbody :key="index" v-for="(item, index) in historyList">
                    <tr>
                      <td class="text-center">{{index+1}}</td>
                      <td class="text-center">{{item}}</td>

                    </tr>
                  </tbody>
                </table>
              </div>
              <div v-else>
                <h5>No transaction history.</h5>
              </div>
            </div>
          </tab-pane>
          <tab-pane title="Message Box">
            <div>
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
                        <base-button type="primary" class="my-4" @click="bargainingShow = false;giveNewPrice()">OK
                        </base-button>
                        <base-button type="primary" class="my-4" @click="bargainingShow = false; clearAllInfo()">Close
                        </base-button>
                      </div>
                    </form>
                  </template>
                </card>
              </modal>
            </div>
            <div>
              <div v-if="pendingTransactionRequestList.length > 0">
                <table class="table">
                  <thead>
                    <tr>
                      <th class="text-center">#</th>
                      <th class="text-center">From</th>
                      <th class="text-center">To</th>
                      <th class="text-center">Electricity Amount</th>
                      <th class="text-center">Electricity Price</th>
                      <th class="text-center">Actions</th>
                    </tr>
                  </thead>
                  <tbody :key="index" v-for="(item, index) in pendingTransactionRequestList">
                    <tr>
                      <td class="text-center">{{index+1}}</td>
                      <td class="text-center">{{item.from}}</td>
                      <td class="text-center">{{item.to}}</td>
                      <td class="text-center">{{item.amount}}</td>
                      <td class="text-center">{{item.price}}</td>
                      <td class="td-actions text-center">
                        <button v-show="item.bargainingUser == userInfo.accountId" type="button" rel="tooltip"
                          class="btn btn-info btn-sm btn-icon " @click="actionName = true;bargaining(item);">
                          BARGAINING
                        </button>
                        <button v-show="item.bargainingUser == userInfo.accountId" type="button" rel="tooltip"
                          class="btn btn-success btn-sm btn-icon " @click="actionName = false;confirmTransaction(item)">
                          CONFIRM
                        </button>
                        <button type="button" rel="tooltip" class="btn btn-warning btn-sm btn-icon "
                          @click="actionName = false;rejectTransaction(item)">
                          REJECT
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div v-else>
                <h5>No pending transaction.</h5>
              </div>
            </div>
          </tab-pane>
        </card>
      </tabs>
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
  data () {
    return {
      transDShow: false,    //if activate dialog
      confirmDShow: false,
      bargainingShow: false,
      actionName: Boolean,
      amount: null,
      tabName: "",
      userInfo: {
        accountId: null,
        elecAmount: null,
        balance: null
      },
      queryName: Store.getItem("dis-elec-tran-name"),
      userIdentity: Store.getItem("dis-elec-tran-identity"),
      inName: null,
      transactionInfo: {
        toAccount: null,
        fromAccount: null,
        elecAmount: null,
        elecPrice: 1
      },
      bargainingPrice: null,
      bargainingId: null,
      userList: [],
      historyList: [],
      bargainingUser: null,
      transactionRequestList: [],
      pendingTransactionRequestList: []
    }
  },
  created () {
    this.getUserInfo();
    this.hasCache();
    this.getAllUser();
    this.getTransactionRequest();
  },
  filters: {
    nullValue (str) {
      return str || "Null";
    }
  },
  methods: {
    handleTabClick (key) {
      console.log(this.tabName)
    },
    hasCache () {
      console.log(Store.getItem("dis-elec-tran-name"))
      if (Store.getItem("dis-elec-tran-name") == null) {
        this.$router.push("/login")
      }
    },
    logOut () {
      if (Store.getItem("dis-elec-tran-name") != null) {
        window.localStorage.removeItem("dis-elec-tran-name")
        window.localStorage.removeItem("dis-elec-tran-identity")
      }

      this.$router.push("/login")
    },
    async getUserInfo () {
      await this.$axios({
        method: "get",
        url: `/userInfo/${this.queryName}`
      }).then((resp) => {
        console.log(resp)
        this.userInfo = resp.data.result;
      })
    },
    Transaction () {
      this.$axios({
        method: "post",
        url: `/transaction/transfer`,
        params: {
          fromAccount: this.transactionInfo.fromAccount,
          toAccount: this.transactionInfo.toAccount,
          elecAmount: this.transactionInfo.elecAmount,
          elecPrice: this.transactionInfo.elecPrice
        }
      }).then((resp) => {
        console.log(resp)
        this.clearAllInfo()
        this.getUserInfo()
        this.getAllUser()
        this.getTransactionRequest();
        this.confirmDShow = false;
      })
    },
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
          bargainingUser: this.userInfo.accountId == this.transactionInfo.fromAccount ? this.transactionInfo.toAccount : this.transactionInfo.fromAccount,
          status: 0,
        }
      }).then((resp) => {
        console.log(resp)
        this.clearAllInfo()
        this.getUserInfo()
        this.getAllUser()
        this.getTransactionRequest();
        this.confirmDShow = false;
      })
    },
    giveNewPrice () {
      this.$axios({
        method: "post",
        url: `/update`,
        params: {
          id: this.bargainingId,
          price: this.bargainingPrice,
          bargainingUser: this.bargainingUser,
          status: 0,
        }
      }).then((resp) => {
        console.log(resp)
        this.clearAllInfo()
        this.getUserInfo()
        this.getAllUser()
        this.getTransactionRequest();
        this.bargainingShow = false;
      })
    },
    rejectTransaction (transaction) {
      this.$axios({
        method: "post",
        url: `/update`,
        params: {
          id: transaction.id,
          price: transaction.price,
          status: 2,
        }
      }).then((resp) => {
        console.log(resp)
        this.clearAllInfo()
        this.getUserInfo()
        this.getAllUser()
        this.getTransactionRequest();
      })
    },
    confirmTransaction (transaction) {
      this.$axios({
        method: "post",
        url: `/transaction/transfer`,
        params: {
          fromAccount: transaction.from,
          toAccount: transaction.to,
          elecAmount: transaction.amount,
          elecPrice: transaction.price,
        }
      }).then((resp) => {
        console.log(resp)
        this.clearAllInfo()
        this.getUserInfo()
        this.getAllUser()
        this.getTransactionRequest();

        // transaction success
        this.$axios({
          method: "post",
          url: `/update`,
          params: {
            id: transaction.id,
            price: transaction.price,
            status: 1,
          }
        }).then((resp) => {
          console.log(resp)
          this.clearAllInfo()
          this.getUserInfo()
          this.getAllUser()
          this.getTransactionRequest();
        })
      })
    },
    async getAllUser () {
      await this.$axios({
        method: "get",
        url: `/transaction/queryall/${"useronly"}/${this.queryName}`
      }).then((resp) => {
        console.log(resp)
        console.log(resp.data.result)
        this.userList = resp.data.result
      })
    },
    async getHistory () {
      await this.$axios({
        method: "get",
        url: `/History/${this.queryName}`
      }).then((resp) => {
        console.log(resp)
        console.log(resp.data.result)
        this.historyList = resp.data.result
      })
    },
    async getTransactionRequest () {
      await this.$axios({
        method: "get",
        url: `/query/${this.queryName}`
      }).then((resp) => {
        console.log(resp)
        console.log(resp.data.result)
        this.transactionRequestList = resp.data.result
        this.pendingTransactionRequestList = this.transactionRequestList.filter((item) => {
          return item.status == 0
        })
      })
    },
    getTabIndex (res) {
      switch (res) {
        case 0:
          this.getAllUser();
          this.getTransactionRequest();
          break;
        case 1:
          this.getHistory();
          break;
        case 2:
          this.getTransactionRequest();
          break;
      }
    },
    transDialog (transUser) {
      this.transDShow = true;
      if (this.actionName) {
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
    }
  }
};
</script>

<style>
#head {
  display: flex;
  justify-content: space-between;
  height: fit-content;
}

#leftTitle {
  width: 70%;
  display: flex;
  justify-content: center;
}

#rightInfo {
  margin-top: 2%;
  margin-right: 70px;
  width: 30%;
  float: right;
  padding-left: 8%;
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

.container {
  min-width: 85%;
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
</style>