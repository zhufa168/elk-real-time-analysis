<template>
  <div style="display: flex;flex-wrap: wrap;">
    <div v-for="asset in assetsList" :key="asset"  style="padding: 40px; margin: 20px 15px; border-radius: 7px; display: inline-grid; background-color: #f5f7fa">
      <img
        style="width: 100px; height: 100px"
        src="@/assets/images/assets.svg">
      <span style="font-size: 15px;padding: 10px 0px">{{asset}}</span>
    </div>
  </div>
</template>
<script>
import { assetsList } from "@/api/search/assets";

export default {
  name: "assets",
  data() {
    return {
      //遮掩层
      loading: true,
      assetsList: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      assetsList().then(response => {
        this.assetsList = response.data;
        this.loading = false;
      });
    }
  }
}
</script>
