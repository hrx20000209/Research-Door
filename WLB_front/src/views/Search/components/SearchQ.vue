<template>
  <div>
    <div v-for="item in questions" :key="item.id">
      <question-search-result
          :question-id="item.questionId"
          :create-time="item.createTime"
          :owner-id="item.ownerId"
          :author="item.userName"
          :description="item.description"
          :title="item.title"
          :follow-cnt="item.followNum"
          :keywords="item.keywords"
          :read-cnt="item.readNum"
          :answer-cnt="item.ansNum"
          :recommend-cnt="item.recNum"
          :is-following="item.isFollowing"
          :is-recommend="item.isRec"
          :user-description="item.introduction"
      ></question-search-result>
    </div>
  </div> 
</template>
  
<script>
import QuestionSearchResult from "@/components/Question/QuestionSearchResult";
export default {
  name: "SearchQ",
  components: {QuestionSearchResult},
  created() {
    this.userId = parseInt(sessionStorage.getItem("userId"))
    let keyword = this.$route.query.keyword;
    if (keyword === '') this.$message.error("Please Input Keyword")
    else this.searchQuestion(keyword)
  },
  data() {
    return {
      userId: 1,
      questions: [],
      results: [
        {
          title: "Would there be any significant alteration in ASD like symptoms observed " +
            "in rats if ICV administration of 1M PPA is done bilaterally?",
          date: "Jul 2022",
          description: "Unilateral ICV administration of 1M Propanoic acid is generally done " +
            "in rats to induce autism like symptoms.",
          answerCnt: 1,
          readCnt: 60,
          recommendCnt: 0,
        },
        {
          title: "How the covid-19 pandemic has impacted the lives and routine of TEA families around the world?",
          date: "Jul 2022",
          description: "The covid-19 pandemic has impacted the lives and routine of families globally. " +
            "Considering the specificities of families of people with Autism Spectrum Disorder (ASD), " +
            "how to analyze the effectiveness of family ties developed in the context of social isolation " +
            "by families...",
          answerCnt: 1,
          readCnt: 60,
          recommendCnt: 3,
        }
      ]
    }
  },
  methods: {
    async searchQuestion(keyword) {
      let params = {
        userId: this.userId,
        keyword: keyword
      }
      const {data:res} = await this.$http.get("/question/searchQuestion", {params});
      this.questions = res.data.reverse()
    },
  }
}
</script>
  
<style scoped>

</style>