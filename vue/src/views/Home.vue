<template>
    <div>

        <p>gethere</p>
        <p>{{line}}</p>

    </div>
</template>

<script>
export default {
  name: "home",
  components: {},
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
        this.line= resp.data.msg + "/n" +resp.data.page;
      })
    },
  },
};
</script>
