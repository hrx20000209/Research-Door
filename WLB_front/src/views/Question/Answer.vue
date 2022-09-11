<template>
  <div class="answer-body">
    <Navbar></Navbar>
    <div class="question-box">
      <question-details
          :is-following="questionInfo[0].isFollowing"
          :author="questionInfo[0].userName"
          :user-description="questionInfo[0].introduction"
          :is-recommend="questionInfo[0].isRec"
          :question-id="questionInfo[0].questionId"
          :description="questionInfo[0].description"
          :title="questionInfo[0].title"
          :follow-cnt="questionInfo[0].followNum"
          :owner-id="questionInfo[0].ownerId"
          :recommend-cnt="questionInfo[0].recNum"
          :answer-cnt="questionInfo[0].ansNum"
          :read-cnt="questionInfo[0].readNum"
          :keywords="questionInfo[0].keywords"
          :create-time="questionInfo[0].createTime"
      ></question-details>
    </div>
    <div class="answer-list-box">
      <div class="all-replies">
        All replies({{ questionInfo[0].ansNum }})
      </div>
      <el-divider></el-divider>
      <div v-for="item in answers" :key="item.id">
        <answer-item
          :user-name="item.userName"
          :answer-id="item.answerId"
          :introcdutcion="item.introduction"
          :owner-id="item.ownerId"
          :content="item.content"
          :question-id="item.questionId"
          :is-rec="item.isRec"
          :is-following="item.isFollowing"
          :follow-num="item.followNum"
          :rec-num="item.recNum"
        ></answer-item>
        <el-divider></el-divider>
      </div>
      <div class="edit-answer-box">
        <div class="top">
          <el-avatar shape="circle" :size="size" :src="head"></el-avatar>
          <div class="name-box">
            <div class="author-name" @click="toUserPage">{{ user.name }}</div>
            <div class="author-description">{{ user.introduction }}</div>
          </div>
        </div>
        <div class="reply-line">
          Reply
        </div>
        <div>
          <quill-editor
              id="quill-editor"
              v-model="text"
              :options="editorOption"
              class="editor"
          >
          </quill-editor>
        </div>
        <div class="add-btn-box">
          <el-button type="primary" class="add-btn" @click="answerQuestion">Add</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AnswerItem from "@/components/Question/AnswerItem";
import QuestionDetails from "@/components/Question/QuestionDetails";
import Navbar from "@/components/Navbar";

export default {
  name: "Answer",
  components: {QuestionDetails, AnswerItem, Navbar},
  created() {
    this.questionId = this.$route.query.questionId
    this.user.id = parseInt(sessionStorage.getItem("userId"))
    this.user.name = sessionStorage.getItem("userName")
    this.user.description = sessionStorage.getItem("introduction")
    this.getQuestionDetails()
    this.getAnswers()
  },

  data() {
    return {
      size: 50,
      text: '',
      userId: 1,
      questionId: 0,
      head: require("../../assets/images/Question/head.png"),
      editorOption: {
        modules: {
          toolbar:[
            ['bold', 'italic'],    //加粗，斜体
            [{ 'script': 'sub'}, { 'script': 'super' }],   // 上下标
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],     //列表
          ],
        },
        placeholder: "Contribute to the discussion",
        theme: 'snow'
      },
      user: {
        id: 1,
        name: "Huang Runxi",
        description: "Beihang University (BUAA)"
      },
      questionInfo: [],
      answers: []
    }
  },
  methods: {
    async answerQuestion() {
      if (this.text === '') {
        this.$message({
          message: 'You can not add empty answer!',
          type: 'warning'
        })
      } else {
        await this.addAnswer()
        this.text = ''
        location.reload()
      }
    },
    toUserPage() {
      this.$router.push({
        path: '/userinfo',
        query: {
          userId: this.user.id
        }
      })
    },

    async getQuestionDetails() {
      let params = {
        questionId: this.questionId,
        userId: this.user.id
      }
      const {data:res} = await this.$http.get("/question/getQuestion", {params});
      this.questionInfo.push(res.data)
    },
    async getAnswers() {
      let params = {
        questionId: this.questionId,
        userId: this.user.id
      }
      const {data:res} = await this.$http.get("/answer/getAnswers", {params});
      this.answers = res.data
      console.log(this.answers)
    },
    async addAnswer() {
      let form = {
        questionId: this.questionId,
        ownerId: this.user.id,
        content: this.text
      }
      const {data:res} = await this.$http.post("/answer/addAnswer", form);
      if (res.code === 200) {
        this.$message({
          message: "Add answer successfully!",
          type: "success"
        })
      }
    },
  }
}
</script>

<style scoped>
/deep/ .ql-container{
  min-height: 150px ;
}

.answer-body {
  font-family: "Roboto",Arial,sans-serif;
  background-color: #f4f4f4;
}

.question-box {
  margin: 1% 0 3% 0;
}

.answer-list-box {
  box-shadow: 0 0 5px 1px rgba(0, 0, 0, 0.3);
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  width: 50%;
  background-color: white;
}

.all-replies {
  padding: 4% 4% 0 4%;
  display: flex;
  flex-direction: row;
  font-size: 20px;
}

.top {
  display: flex;
  flex-direction: row;
}

.name-box {
  top: 0;
  bottom: 0;
  margin: auto;
  width: 85%;
}

.author-name {
  display: flex;
  flex-direction: row;
  justify-content: left;
  font-weight: bold;
}

.author-description {
  display: flex;
  flex-direction: row;
}

.edit-answer-box {
  background-color: white;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  padding: 0 4% 0 4%;
}

.reply-line {
  font-size: 20px;
  font-weight: bold;
  text-align: left;
  margin: 1% 0 1% 0;
}

.editor {
  height: 150%;
}

.add-btn-box {
  margin: 2% 0 2% 0;
  display: flex;
  flex-direction: row;
  justify-content: right;
}

.add-btn {
  height: 100%;
  width: 15%;
}
</style>