<template>
  <div>
    <div class="select">
      <el-select v-model="searchType" size="small" @change="changeType">
        <el-option value="0" label="All"/>
        <el-option value="1" label="Article"/>
        <el-option value="2" label="Patent"/>
        <el-option value="3" label="Report"/>
      </el-select>
    </div>
    <div v-for="item in results" :key="item.id">
      <ach-search-result
        :id="item.id"
        :title="item.title"
        :type="item.type"
        :isAvailable="item.isAvailable"
        :date="item.date"
        :author="item.author"
        :abstract="item.abstract"
        :cite-cnt="item.citeCnt"
        :read-cnt="item.readCnt"
        :recommend-cnt="item.recommendCnt"
        :isFollowed="item.isFollowed"
        :isAuthor="item.isAuthor"
      ></ach-search-result>
    </div>
  </div> 
</template>
    
<script>
import { domainToUnicode } from "url";
import AchSearchResult from "../components/AchSearchResult.vue";
export default {
  name: "SearchAch",
  components: {AchSearchResult},
  data() {
    return {
      searchType: "0",
      results: [
        
      ]
    }
  },
  created(){
    let keyword = this.$route.query.keyword;
    if(keyword == '') this.$message.error("Please Input Keyword");
    else this.searchAch(keyword);
  },
  methods: {
    changeType(){
      let keyword = this.$route.query.keyword;
      if(keyword == '') this.$message.error("Please Input Keyword");
      else this.searchAch(keyword);
    },
    async searchAch(keyword){
      let param = {
        userId: sessionStorage.getItem("userId"),
        keyword: keyword,
        type: this.searchType,
      }
      const {data:res} = await this.$http.post("/api/achievement/searchAch", param);
      console.log(res.data[0].id);
      if (res.status === 200){
        this.results = res.data;
      }
      else {
        this.$message({
          message: 'Fail',
          type: 'error',
        });
      }
    }
  }
}
</script>
    
<style scoped>
.select {
  text-align: left;
  padding-top: 2%;
  margin-left: 2%;
  margin-bottom: 2%;
}
</style>