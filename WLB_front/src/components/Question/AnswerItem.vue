<template>
  <div style="padding: 0 4% 0 4%">
    <div class="top">
      <el-avatar shape="circle" :size="size" :src="head"></el-avatar>
      <div class="name-box">
        <div class="info">
          <div class="author-name" @click="toUserPage">{{ userName }}</div>
          <div> &nbsp added a reply </div>
        </div>
      </div>
      <div class="time-box">
        {{ createTime }}
      </div>
    </div>
    <div class="answer-text-box" v-html="content"></div>
    <div class="bottom-btn">
      <div class="follow-recommend-btn">
        <div v-if="isRecommend" class="share-btn" @click="cancelRecommend">
          Recommended
        </div>
        <div v-else class="share-btn" @click="recommend">
          Recommend
        </div>
        <div v-if="isFollowed" class="share-btn" @click="unfollow">
          Following
        </div>
        <div v-else class="share-btn" @click="follow">
          Follow
        </div>
        <div class="data-box">
          {{ recommendCnt }} Recommendations
        </div>
      </div>
      <div class="edit-delete-btn" v-if="userId===ownerId">
        <el-dropdown trigger="click" @command="handleCommand">
            <span class="el-dropdown-link">
              <img :src="downImage" class="down-icon" alt="">
            </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="a">Edit</el-dropdown-item>
            <el-dropdown-item command="b">Delete</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>

      <el-dialog
          :visible.sync="editVisible"
          width="50%">
        <div slot="title" class="question-title-box">
          <div>
            Edit your answer
          </div>
        </div>
        <div>
          <quill-editor
              id="quill-editor"
              v-model="newAnswer"
              :options="editorOption"
              class="editor-discussion"
          >
          </quill-editor>
        </div>
        <span slot="footer" class="dialog-footer">
        <el-button @click="closeEdit">Cancel</el-button>
        <el-button type="primary" @click="editAnswer">Save</el-button>
      </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "AnswerItem",
  props: ["isFollowing", "isRec", "answerId", "content", "ownerId", "questionId", "recNum", "followNum", "createTime",
    "userName"],
  created() {
    this.userId = parseInt(sessionStorage.getItem("userId"))
    if (this.createTime !== undefined && this.createTime !== null) {
      let time = this.createTime.split("-")
      let day = time[2].split("T")[0]
      const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
      this.createDate = months[time[1] - 1] + " " + day
    }
    this.isFollowed = this.isFollowing === 1
    this.isRecommend = this.isRec === 1
    this.recommendCnt = this.recNum
  },
  data() {
    return {
      userId: 1,
      size: 40,
      recommendCnt: 0,
      head: require("../../assets/images/Question/head.png"),
      downImage: require("../../assets/images/Question/down.png"),
      newAnswer: '',
      isFollowed: false,
      isRecommend: false,
      editVisible: false,
      editorOption: {
        modules: {
          toolbar:[
            ['bold', 'italic'],    //加粗，斜体
            [{ 'script': 'sub'}, { 'script': 'super' }],   // 上下标
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],     //列表
          ],
        },
        placeholder: "Enter an explanation or any details needed to understand your question",
        theme: 'snow'
      },
    }
  },
  methods: {
    follow() {
      this.isFollowed = true
      this.followAnswer()
    },
    unfollow() {
      this.isFollowed = false
      this.unfollowAnswer()
    },
    recommend() {
      this.isRecommend = true
      this.recommendCnt += 1
      this.recommendAnswer()
    },
    cancelRecommend() {
      this.isRecommend = false
      this.recommendCnt -= 1
      this.cancelRecommendAnswer()
    },
    handleCommand(command) {
      if (command === 'a') {
        this.editVisible = true
        this.newAnswer = this.content
      } else if (command === 'b') {
        {
          this.$alert('Are you sure you want to delete this answer?', 'Delete question', {
            confirmButtonText: 'Delete',
            cancelButtonText: "Cancel",
            showCancelButton: true,
            callback: action => {
              if (action === "confirm") {
                this.delAnswer()
              }
            }
          });
        }
      }
    },
    closeEdit() {
      this.editVisible = false
    },
    editAnswer() {
      this.updateAnswer()
      this.closeEdit()
    },
    toUserPage() {
      this.$router.push({
        path: '/userinfo',
        query: {
          userId: this.ownerId
        }
      })
    },

    async recommendAnswer() {
      let form = {
        userId: this.userId,
        answerId: this.answerId
      }
      const {data:res} = await this.$http.post("/answer/recAnswer", form);
      if (res.status === 200) {
        this.$message({
          message: "Recommend successfully!",
          type: "success"
        })
      }
    },
    async cancelRecommendAnswer() {
      let form = {
        userId: this.userId,
        answerId: this.answerId
      }
      const {data:res} = await this.$http.post("/answer/unrecAnswer", form);
      if (res.status === 200) {
        this.$message({
          message: "Cancel recommendation successfully!",
          type: "success"
        })
      }
    },
    async followAnswer() {
      let form = {
        userId: this.userId,
        answerId: this.answerId
      }
      const {data:res} = await this.$http.post("/answer/followAnswer", form);
      if (res.status === 200) {
        this.$message({
          message: "Follow successfully!",
          type: "success"
        })
      }
    },
    async unfollowAnswer() {
      let form = {
        userId: this.userId,
        answerId: this.answerId
      }
      const {data:res} = await this.$http.post("/answer/unfollowAnswer", form);
      if (res.status === 200) {
        this.$message({
          message: "Unfollow successfully!",
          type: "success"
        })
      }
    },
    async delAnswer() {
      let form = {
        answerId: this.answerId
      }
      const {data:res} = await this.$http.post("/answer/delAnswer", form);
      if (res.status === 200) {
        this.$message({
          message: "Delete successfully!",
          type: "success"
        })
      }
      location.reload()
    },
    async updateAnswer() {
      let form = {
        answerId: this.answerId,
        ownerId: this.userId,
        content: this.newAnswer
      }
      const {data:res} = await this.$http.post("/answer/updateAnswer", form);
      if (res.status === 200) {
        this.$message({
          message: "Update answer successfully!",
          type: "success"
        })
      }
      location.reload()
    }
  }
}
</script>

<style scoped>
.top {
  display: flex;
  flex-direction: row;
}

.name-box {
  top: 0;
  bottom: 0;
  margin: auto;
  width: 75%;
}

.info {
  display: flex;
  flex-direction: row;
  justify-content: left;
  margin-left: 2%;
}

.author-name {
  font-weight: bold;
}

.time-box {
  display: flex;
  flex-direction: row;
  justify-content: right;
  top: 0;
  bottom: 0;
  margin: auto;
  width: 20%;
  font-size: 15px;
  color: grey;
}

.answer-text-box {
  display: flex;
  flex-direction: row;
  margin-top: 2%;
}

.share-btn {
  display: flex;
  flex-direction: row;
  margin: 2% 2% 0 0;
  color: dodgerblue;
}

.share-btn:hover {
  cursor: pointer;
  text-decoration: underline;
  color: darkblue;
}

.bottom-btn {
  display: flex;
  flex-direction: row;
}

.follow-recommend-btn {
  width: 80%;
  display: flex;
  flex-direction: row;
}

.edit-delete-btn {
  width: 20%;
  display: flex;
  flex-direction: row;
  justify-content: right;
  top: 0;
  bottom: 0;
  margin: auto;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}

.down-icon {
  width: 20px;
}

.down-icon:hover {
  cursor: pointer;
}

.data-box {
  width: 35%;
  margin: 2% 2% 0 5%;
  color: lightslategrey;
}
</style>