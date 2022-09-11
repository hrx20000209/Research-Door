<template>
  <div>
    <div v-for="item in questions" :key="item.id">
      <question-item
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
      ></question-item>
    </div>
  </div>
</template>

<script>
import QuestionItem from "@/components/Question/QuestionItem";

export default {
  name: "QuestionYouAsk",
  components: { QuestionItem },
  async created() {
    this.userId = parseInt(sessionStorage.getItem("userId"))
    this.userName = sessionStorage.getItem("userName")
    await this.getAskedQuestions()
  },
  data() {
    return {
      userId: 1,
      userName: "",
      questions: []
    }
  },
  methods: {
    async getAskedQuestions() {
      const userId = this.userId,
      params = {
        userId
      }
      const {data:res} = await this.$http.get("/question/getAskedQuestion", {params});
      this.questions = res.data.reverse()
    },
  }
}
</script>

<style scoped>

</style>