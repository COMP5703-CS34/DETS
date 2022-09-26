<template>
  <div>
    <div id = "head">
      <div id = "leftTitle">
        <h1 class="display-3  title">Distributed Electricity Transaction Platform</h1>
      </div>
      <div id = "rightInfo">
        <p>Account ID: {{this.userInfo.accountId | nullValue}}</p>
        <p>Electricity Amount: {{this.userInfo.elecAmount | nullValue}}</p>
        <p>Balance: {{this.userInfo.balance | nullValue}}</p>
      </div>
    </div>
    <div>
      <tabs fill class="container d-flex">
        <card shadow>
            <tab-pane title="User Infomation">
              <div class="modal-body">
                <md-content md-dynamic-height md-alignment-top-center>
                  <p>
                    please input the account name you want:
                  </p>
                </md-content>
                <!-- messageClass is to show if there is invalid input -->
                <!-- md-field :class="messageClass" > 
                  <!- inputQuantity is to monitor input validation -->
                  <input v-model="inName">
                  <!-- show error hint if there is one, judge by disable.value -->
                  <!-- md-content class="md-error" v-if="disabled.value" v-model="error.message">{{error.message}}</!-->
                
              </div>
              <button class="md-primary" @click="QueryTest">Query</button>
              <div class="modal-body">
                <md-content md-dynamic-height md-alignment-top-center>
                  <p>
                    please input the transaction Info you want:
                  </p>
                </md-content>
                <!-- messageClass is to show if there is invalid input -->
                <!-- md-field :class="messageClass" > 
                  <!- inputQuantity is to monitor input validation -->
                  <div class = "inputDiv">
                    <p>From: </p>
                    <input class="tans" v-model="transactionInfo.fromAccount" placeholder="fromAccount">
                  </div>
                  <div class = "inputDiv">
                    <p>To </p>
                    <input class="tans" v-model="transactionInfo.toAccount" placeholder="toAccount">
                  </div>
                  <div class = "inputDiv">
                    <p>elecAmount: </p>
                    <input class="tans" v-model="transactionInfo.elecAmount" placeholder="elecAmount">
                  </div>
                  <div class = "inputDiv">
                    <p>elecPrice: </p>
                    <input class="tans" v-model="transactionInfo.elecPrice" placeholder="elecPrice">
                  </div>
                  <!-- show error hint if there is one, judge by disable.value -->
                  <!-- md-content class="md-error" v-if="disabled.value" v-model="error.message">{{error.message}}</!-->
                
              </div>
              <button class="md-primary" @click="TransactionTest">Transaction</button>
            </tab-pane>

            <tab-pane title="Transaction">
                <p class="description">Cosby sweater eu banh mi, qui irure terry richardson ex
                    squid. Aliquip placeat salvia cillum iphone. Seitan aliquip quis cardigan
                    american apparel, butcher voluptate nisi qui.</p>
            </tab-pane>

            <tab-pane title="History">
                <p class="description">Raw denim you probably haven't heard of them jean shorts
                    Austin. Nesciunt tofu stumptown aliqua, retro synth master cleanse. Mustache
                    cliche tempor, williamsburg carles vegan helvetica. Reprehenderit butcher retro
                    keffiyeh dreamcatcher synth.</p>
            </tab-pane>
        </card>
      </tabs>
    </div>
  </div>
</template>

<script>
  import Tabs from "@/components/Tabs/Tabs.vue";
  import TabPane from "@/components/Tabs/TabPane.vue";
  export default {
    name: "home",
    components: {
      Tabs,
      TabPane
    },
    data() {
      return {
          userInfo: {
            accountId: null,
            elecAmount: null,
            balance: null
          },
          queryName: "Home",
          inName: null,
          transactionInfo: {
            toAccount: null,
            fromAccount: null, 
            elecAmount: null, 
            elecPrice: null
          }
        }
    },
    created() {
      this.getUserInfo();
    },
    filters: {
      nullValue(str) {
        return str || "Null";
      }
    },
    methods: {
      async getUserInfo(name = this.queryName) {
        name = String(name)
        await this.$axios({
          method: "get",
          //url: `/test`
          url: `/userInfo/${name}`
        }).then((resp) => {
          console.log(resp)
          this.userInfo= resp.data.result;
        })
      },
      QueryTest() {
        this.getUserInfo(this.inName)
      },
      TransactionTest() {
        this.$axios({
          method: "post",
          url: `/doTransaction`,
          params: {
            fromAccount: this.transactionInfo.fromAccount,
            toAccount: this.transactionInfo.toAccount,
            elecAmount: this.transactionInfo.elecAmount,
            elecPrice: this.transactionInfo.elecPrice
          }
        }).then((resp) => {
          console.log(resp)
        })
      }
    },
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
    margin-top: 5%;
    margin-right: 60px;
    width: 30%;
    float: right;
    padding-left: 8%;
    text-align: right;
  }
  .title{
    margin-top: 30px;
    margin-left: 60px;
    margin-bottom: 50px;
    color: #6A5ACD;
  }
  .container{
    min-width: 85%;
  }
  .inputDiv{
    display: flex;
    justify-content: space-between;
    width: 40%;
  }
  .trans{
    width: 70%;
    float: right;
    justify-content: end;
  }
</style>