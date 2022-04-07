<template>
  <div style="overflow: hidden">
    <div style="margin: 20px; display: flex;overflow-x: scroll;overflow-y: hidden; -webkit-overflow-scrolling:touch;">
        <div v-for="attachHeader in attachHeaderList" style="width: 120px; height: 70px;padding: 20px;">
          <span>{{attachHeader.attNameEn}}</span>
        </div>
    </div>
  </div>
</template>
<script>
import { attachHeaderList } from "@/api/search/attachHeader";

export default {
  name: "AttCk",
  props: {

  },
  data() {
    return {
      attachHeaderList: []
    }
  },
  computed: {

  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      attachHeaderList().then(response => {
        this.attachHeaderList = response.data;
      });
    }
  }
}

</script>
<style scoped lang="scss">
</style>
