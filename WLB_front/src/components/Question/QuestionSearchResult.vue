<template>
  <div class="main">
    <div style="padding: 4%">
      <div class="title" @click="reply">
        {{ title }}
      </div>
      <div class="second-line">
        <div class="green-box">
          Question
        </div>
        <div class="date-box">
          {{ createDate }}
        </div>
      </div>
      <div class="description-box" v-html="description"></div>
      <div class="data-box">
        <div v-if="recommendCnt!==0">
          {{ recommendNum }} Recommendations ·&nbsp
        </div>
        <div>
          {{ answerCnt }} Answer ·&nbsp
        </div>
        <div>
          {{ readCnt }} Reads
        </div>
      </div>
      <div class="bottom">
        <div>
          <el-button type="primary" plain>Answer</el-button>
        </div>
        <div style="width: 50%"></div>
        <div class="btn-box">
          <div v-if="isRecommended" class="text-btn" @click="cancelRecommend">
            Recommended
          </div>
          <div v-else class="text-btn" @click="recommend">
            Recommend
          </div>
          <div v-if="isFollowed" class="text-btn" @click="unfollow">
            Following
          </div>
          <div v-else class="text-btn" @click="follow">
            Follow
          </div>
          <div class="text-btn" @click="shareLink">
            Share
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "QuestionSearchResult",
  props: ['keywords', 'title', 'author', 'answerCnt', 'description', 'createTime', "readCnt", "type", "questionId",
    "ownerId", "recommendCnt", "followCnt", "userDescription", "isFollowing", "isRecommend" ],
  created() {
    this.userId = parseInt(sessionStorage.getItem("userId"))
    if (this.createTime !== undefined) {
      let time = this.createTime.split("-")
      let day = time[2].split("T")[0]
      const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
      this.createDate = months[time[1] - 1] + " " + day
    }
    this.isFollowed = this.isFollowing === 1
    this.isRecommended = this.isRecommend === 1
    this.recommendNum = this.recommendCnt
  },
  data() {
    return {
      head: require("../../assets/images/Question/head.png"),
      userId: 1,
      createDate: "",
      isFollowed: false,
      isRecommended: false,
      recommendNum: 0,
    }
  },
  methods: {
    follow() {
      this.isFollowed = true
      this.followQuestion()
    },
    unfollow() {
      this.isFollowed = false
      this.unfollowQuestion()
    },
    recommend() {
      this.isRecommended = true
      this.recommendNum += 1
      this.recommendQuestion()
    },
    cancelRecommend() {
      this.isRecommended = false
      this.recommendNum -= 1
      this.cancelRecommendQuestion()
    },
    reply() {
      this.$router.push({
        path: '/answer',
        query: {
          questionId: this.questionId
        }
      })
    },
    shareLink() {
      this.$alert('http://43.140.248.122:8080/#/answer?questionId=' + this.questionId,
          'Copy the link and share', {
            confirmButtonText: 'OK',
            callback: action => {
            }
          });
    },

    async followQuestion() {
      let form = {
        userId: this.userId,
        questionId: this.questionId
      }
      const {data:res} = await this.$http.post("/question/followQuestion", form);
      if (res.status === 200) {
        this.$message({
          message: "Follow successfully!",
          type: "success"
        })
      }
    },
    async unfollowQuestion() {
      let form = {
        userId: this.userId,
        questionId: this.questionId
      }
      const {data:res} = await this.$http.post("/question/unfollowQuestion", form);
      if (res.status === 200) {
        this.$message({
          message: "Unfollow successfully!",
          type: "success"
        })
      }
    },
    async recommendQuestion() {
      let form = {
        userId: this.userId,
        questionId: this.questionId
      }
      const {data:res} = await this.$http.post("/question/recQuestion", form);
      if (res.status === 200) {
        this.$message({
          message: "Recommend successfully!",
          type: "success"
        })
      }
    },
    async cancelRecommendQuestion() {
      let form = {
        userId: this.userId,
        questionId: this.questionId
      }
      const {data:res} = await this.$http.post("/question/unrecQuestion", form);
      if (res.status === 200) {
        this.$message({
          message: "Cancel recommendation successfully!",
          type: "success"
        })
      }
    },
  }
}
</script>

<style scoped>
.main {
  width: 100%;
  background-color: white;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  border-bottom: solid 1px lightgrey;
}

.title {
  text-align: left;
  font-weight: bold;
  font-size: 18px;
}

.title:hover {
  cursor: pointer;
  text-decoration: underline;
}

.second-line {
  display: flex;
  flex-direction: row;
  margin: 1% 0 1% 0;
}

.green-box {
  background-color: #C5E8E5;
  color: #007478;
  text-align: left;
  padding: 1%;
}

.date-box {
  padding: 1%;
  color: grey;
}

.description-box {
  text-align: left;
  margin-bottom: 1%;
}

.data-box {
  text-align: left;
  display: flex;
  flex-direction: row;
  color: lightslategrey;
}

.bottom {
  display: flex;
  flex-direction: row;
  margin: 1% 0 1% 0;
}

.btn-box {
  display: flex;
  flex-direction: row;
  justify-content: right;
  width: 50%;
}

.text-btn {
  text-align: right;

  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
}

.text-btn:hover {
  cursor: pointer;
  text-decoration: underline;
}

</style>