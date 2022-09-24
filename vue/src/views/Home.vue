<template>
  <div>
    <div>
      <h1 class="display-3  title">Distributed Electricity Transaction Platform</h1>
    </div>
    <div>
      <tabs fill class="container d-flex">
        <card shadow>
            <tab-pane title="User Infomation">
                <button>uu</button>
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
          line: null
      }
    },
    created() {
      this.test();
    },

    methods: {
      test() {
        this.$axios({
          method: "get",
          url: "/test"
        }).then((resp) => {
          console.log(resp)
          this.line= resp.data.msg + "\n" +resp.data.page;
        })
      },
    },
  };
</script>

<style>
  .title{
    margin-top: 30px;
    margin-left: 60px;
    margin-bottom: 50px;
    color: #6A5ACD;
  }
  .container{
    min-width: 85%;
  }
</style>