<template>
  <el-card class="main" shadow="hover" style="border-radius: 2vw">
    <div style="padding: 4%" >
      <div class="top">
        <el-avatar shape="circle" :size="size" :src="head"></el-avatar>
        <div class="name-box">
          <div class="author-name">{{ author }}</div>
          <div class="author-description">added an article</div>
        </div>
        <div class="operation-box">
          <el-dropdown trigger="click" @command="handleCommand">
            <span class="el-dropdown-link">
              <img :src="downImage" class="down-icon" alt="">
            </span>
            <el-dropdown-menu slot="dropdown" co>
              <el-dropdown-item command="a">Hide this update</el-dropdown-item>
              <el-dropdown-item command="b">Unfollow {{author}}</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
      <div class="middle">
        <el-divider></el-divider>
        <div class="title-box">
          {{ title }}
        </div>
        <div class="info-box">
          <span class="discussion-box" style="margin-right: 2vw;">
            Article
          </span>
          <span class="text-box">
          {{ description }}
        </span>
        </div>

        <div class="btn-box">
          <el-button  style="height: 40px" @click="request">Request Full Text</el-button>
          <div class="read-cnt-box">
            <div>
             Doi: {{ readCnt }}
            </div>
          </div>
        </div>
        <el-divider></el-divider>
        <div class="tags-box">
          <div v-for="item in keywords" :key="item.id">
            <div class="tag-box">
              {{ item }}
            </div>
          </div>
        </div>
        <div class="footer">
          <div v-if="isRecommend" class="text-btn" style="margin-right: 2%">
            <img :src="recommendingImage" class="image" alt="">
            Recommending
          </div>
          <div v-else class="text-btn" style="margin-right: 2%">
            <img :src="recommendImage" class="image" alt="">
            Recommend
          </div>

          <div v-if="isFollowing" class="text-btn" @click="unfollowQuestion">
            <img :src="followingImage" class="image" alt="">
            Following
          </div>
          <div v-else class="text-btn" @click="followQuestion">
            <img :src="followImage" class="image" alt="">
            Follow
          </div>

          <div class="share-btn">
            <div class="text-btn">
              <i class="el-icon-suitcase-1"></i>
              Save
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-dialog
        :visible.sync="QuestionVisible"
        width="50%">
      <div slot="title" class="question-title-box">
        <div class="blue-title">
          Ask a question
        </div>
        <div>
          &nbsp on ResearchGate
        </div>
      </div>
      <div class="top-sentences">
        <div>
          Enter a clear and concise question that others will easily understand.
        </div>
        <div>
          Please provide any details researchers may need to answer your question.
        </div>
      </div>
      <el-divider></el-divider>
      <div class="question-text">
        <div>Question</div>
      </div>
      <div style="margin-bottom: 1%">
        <el-input
            placeholder="Enter your question"
            v-model="newQuestion.title"
            maxlength="150"
        ></el-input>
      </div>
      <div class="question-text">
        <div>Description</div>
      </div>
      <div>
        <quill-editor
            id="quill-editor"
            v-model="newQuestion.description"
            :options="editorOption"
            class="editor"
        >
        </quill-editor>
      </div>
      <div class="question-text">
        <div>Add some keywords for your question</div>
      </div>
      <el-form :inline="true" :model="newQuestion.keywords" class="demo-form-inline">
        <el-form-item class="keyword-box">
          <el-input v-model="newQuestion.keywords.first" placeholder="keyword 1"></el-input>
        </el-form-item>
        <el-form-item class="keyword-box">
          <el-input v-model="newQuestion.keywords.second" placeholder="keyword 2"></el-input>
        </el-form-item>
        <el-form-item class="keyword-box">
          <el-input v-model="newQuestion.keywords.third" placeholder="keyword 3"></el-input>
        </el-form-item>
        <el-form-item class="keyword-box">
          <el-input v-model="newQuestion.keywords.forth" placeholder="keyword 4"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="close">Cancel</el-button>
        <el-button type="primary" @click="save">Save</el-button>
      </span>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  name: "QuestionDetails",
  props: ['keywords', 'title', 'author', 'head', 'answerCnt', 'description', 'createTime', "readCnt", "type",
    "questionId", "ownerId", "recommendCnt", "followCnt", "userDescription", "isFollowing", "isRecommend" ],
  data() {
    return {
      size: 50,
      activeIndex: "1",
      QuestionVisible: false,
      recommendImage: require("../../assets/images/Question/Arrow up.png"),
      recommendingImage: require("../../assets/images/Question/arrow-up-fill.png"),
      followImage: require("../../assets/images/Question/recommend.png"),
      followingImage: require("../../assets/images/Question/recommend-fill.png"),
      shareImage: require("../../assets/images/Question/forward-arrow.png"),
      downImage: require("../../assets/images/Question/down.png"),
      editorOption: {
        modules: {
          toolbar:[
            ['bold', 'italic'],    //???????????????
            [{ 'script': 'sub'}, { 'script': 'super' }],   // ?????????
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],     //??????
          ],
        },
        placeholder: "Enter an explanation or any details needed to understand your question",
        theme: 'snow'
      },
      newQuestion: {
        title: "",
        description: "",
        keywords: {
          first: "",
          second: "",
          third: "",
          forth: ""
        }
      }
    }
  },
  methods: {
    reply() {
      this.$router.push('/answer')
    },
    request() {
      this.$message({
        message: 'The text will be downloaded in seconds',
        type: 'success'
      });
    },
    close() {
      this.QuestionVisible = false
      this.newQuestion = {
        text: "",
        description: "",
        type: 1
      }
      this.keywords = {
        first: "",
        second: "",
        third: "",
        forth: ""
      }
    },
    handleCommand(command) {
      if (command === 'a') {
        this.QuestionVisible = true
        this.newQuestion.keywords = this.keywords
        this.newQuestion.description = this.description
        this.newQuestion.title = this.title
      } else if (command === 'b') {
        {
          this.$alert('Are you sure you want to delete this question?', 'Delete question', {
            confirmButtonText: 'Delete',
            cancelButtonText: "Cancel",
            showCancelButton: true,
            callback: action => {
              if (action === "confirm") {
                this.delQuestion()
                // this.$router.push('/question')
              }
            }
          });
        }
      }
    },
    save() {
      this.updateQuestion()
      this.QuestionVisible = false
    },
    followQuestion() {
      this.isFollowing = true
    },
    unfollowQuestion() {
      this.isFollowing = false
    },

    async delQuestion() {
      const {data:res} = await this.$http.post("https://10.193.117.4:8090/question/delQuestions", this.questionId);
      if (res.code === 200) {
        this.$message({
          message: "Delete successfully!",
          type: "success"
        })
      }
    },

    async updateQuestion() {
      let form = {
        questionId: this.questionId,
        title: this.newQuestion.title,
        description: this.newQuestion.description,
        keywords: this.newQuestion.keywords
      }
      const {data:res} = await this.$http.post("/question/updateQuestion", form)
      if (res.code === 200) {
        this.$message({
          message: "Update successfully!",
          type: "success"
        })
      }
    }
  }
}
</script>

<style scoped>
.main {
  width: 50%;
  background-color: white;

  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
}

.top {
  padding: 0 0 0 3%;
  display: flex;
  flex-direction: row;
}

.name-box {
  top: 0;
  bottom: 0;
  margin: auto;
  width: 75%;
}

.author-name {
  display: flex;
  flex-direction: row;
  justify-content: left;
  color: dodgerblue;
}

.author-name:hover {
  cursor: pointer;
  text-decoration: underline;
}

.author-description {
  display: flex;
  flex-direction: row;
  justify-content: left;
}

.operation-box {
  top: 0;
  bottom: 0;
  margin: auto;
  font-size: 15px;
  color: grey;
  display: flex;
  flex-direction: row;
}


.operation-btn {
  padding-right: 5%;
}

.middle {
  padding: 0 3% 3% 3%;
}

.title-box {
  font-weight: bolder;
  font-size: 20px;
  display: flex;
  flex-direction: row;
  justify-content: left;
}

.title-box:hover {
  font-weight: bolder;
  font-size: 20px;
  display: flex;
  flex-direction: row;
  justify-content: left;
  cursor: pointer;
  text-decoration: underline;
}

.text-box {
  margin: 1% 0 0 0;
  text-align: left;
}

.info-box {
  display: flex;
  flex-direction: row;
  justify-content: left;
}

.new-box {
  background-color: #00A39E;
  color: white;
  border-radius: 2px;
  font-size: 16px;
  padding: 6px;
  margin: 1% 1% 0 0;
}

.discussion-box {
  background-color: #C5E8E5;
  color: #007478;
  border-radius: 2px;
  font-size: 16px;
  padding: 6px;
  margin: 1% 1% 0 0;
}

.tags-box {
  margin: 1% 0 0 0;
  display: flex;
  flex-direction: row;
  justify-content: left;
  width: 100%;
}

.tag-box {
  margin-right: 15px;
  border-radius: 30px;
  padding: 2px 5px 2px 5px;
  background-color: lightgrey;
}

.btn-box {
  display: flex;
  flex-direction: row;
  justify-content: left;
  margin: 2% 0 0 0;
  width: 100%;
}

.read-cnt-box {
  top: 0;
  bottom: 0;
  margin: auto;
  display: flex;
  flex-direction: row;
  justify-content: right;
  color: grey;
  font-size: 15px;
  width: 100%;
}

.footer {
  display: flex;
  flex-direction: row;
  justify-content: left;
}

.text-btn {
  font-weight: bold;
  display: flex;
  flex-direction: row;
  justify-content: left;
}

.text-btn:hover {
  cursor: pointer;
  text-decoration: underline;
}

.share-btn {
  display: flex;
  flex-direction: row;
  justify-content: right;
  width: 100%;
}

.image {
  width: 20px;
  margin-right: 5px;
}

.down-icon {
  width: 20px;
}

.down-icon:hover {
  cursor: pointer;
}

.tags-box {
  margin: 1% 0 2% 0;
  display: flex;
  flex-direction: row;
  justify-content: left;
  width: 100%;
}

.tag-box {
  margin-right: 15px;
  border-radius: 30px;
  padding: 2px 5px 2px 5px;
  background-color: lightgrey;
}

.question-title-box {
  width: 100%;
  text-align: left;
  display: flex;
  flex-direction: row;
  font-size: 23px;
}

.blue-title {
  color: dodgerblue;
  text-decoration: underline;
}

.top-sentences {
  text-align: left;
}

/deep/.el-dialog__body {
  padding: 1% 3%;
  height: 500px;
}

.question-text {
  text-align: left;
  font-size: 18px;
  font-weight: bold;
  margin: 0 0 1% 0;
}

.editor {
  height: 180px;
  margin-bottom: 55px;
}

.keyword-box {
  width: 20%;
}

.demo-form-inline {
  text-align: left;
}
</style>
