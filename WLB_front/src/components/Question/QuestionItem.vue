<template>
  <div class="main">
    <div style="padding: 4%">
      <div class="top">
        <el-avatar shape="circle" :size="size" :src="head"></el-avatar>
        <div class="name-box">
          <div class="author-name" @click="toUserPage">{{ author }}</div>
          <div class="author-description">{{ userDescription }}</div>
        </div>
        <div class="date-box">
          {{ createDate }}
        </div>
      </div>
      <div class="middle">
        <div class="title-box" @click="reply">
          {{ title }}
        </div>
        <div class="info-box">
          <div class="new-box">
            New
          </div>
          <div class="discussion-box">
            Discussion
          </div>
        </div>
        <div class="text-box" v-html="description"></div>
        <div class="tags-box">
          <div v-for="item in keywordsArray" :key="item.id">
            <div class="tag-box">
              {{ item }}
            </div>
          </div>
        </div>
        <div class="btn-box">
          <el-button type="primary" plain style="height: 40px" @click="reply">Reply</el-button>
          <div class="read-cnt-box">
            <div>
              {{ readCnt }} Reads
            </div>
          </div>
        </div>
        <div class="divider-line"></div>
        <div class="footer">
          <div v-if="isRecommended" class="text-btn" style="margin-right: 2%" @click="cancelRecommend">
            <img :src="recommendedImage" class="image" alt="">
            Recommended
          </div>
          <div v-else class="text-btn" style="margin-right: 2%" @click="recommend">
            <img :src="recommendImage" class="image" alt="">
            Recommend
          </div>

          <div v-if="isFollowed" class="text-btn" @click="unfollow">
            <img :src="followingImage" class="image" alt="">
            Following
          </div>
          <div v-else class="text-btn" @click="follow">
            <img :src="followImage" class="image" alt="">
            Follow
          </div>
          <div class="share-btn">
            <div class="text-btn" @click="shareLink">
              <img :src="shareImage" class="image">
              Share
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "QuestionItem",
  props: ['keywords', 'title', 'author', 'answerCnt', 'description', 'createTime', "readCnt", "type", "questionId",
    "ownerId", "recommendCnt", "followCnt", "userDescription", "isFollowing", "isRecommend" ],
  created() {
    this.userId = parseInt(sessionStorage.getItem("userId"))
    if (this.keywords !== undefined && this.keywords !== null)  this.keywordsArray = this.keywords.split("//")
    if (this.createTime !== undefined && this.createTime !== null) {
      let time = this.createTime.split("-")
      let day = time[2].split("T")[0]
      const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
      this.createDate = months[time[1] - 1] + " " + day
    }
    this.isFollowed = this.isFollowing === 1
    this.isRecommended = this.isRecommend === 1
  },
  data() {
    return {
      userId: 1,
      size: 50,
      createDate: "",
      keywordsArray: [],
      activeIndex: "1",
      isFollowed: false,
      isRecommended: false,
      head: require("../../assets/images/Question/head.png"),
      recommendImage: require("../../assets/images/Question/Arrow up.png"),
      recommendedImage: require("../../assets/images/Question/arrow-up-fill.png"),
      followImage: require("../../assets/images/Question/recommend.png"),
      followingImage: require("../../assets/images/Question/recommend-fill.png"),
      shareImage: require("../../assets/images/Question/forward-arrow.png"),
    }
  },
  methods: {
    reply() {
      this.$router.push({
        path: '/answer',
        query: {
          questionId: this.questionId
        }
      })
    },
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
      this.recommendQuestion()
    },
    cancelRecommend() {
      this.isRecommended = false
      this.cancelRecommendQuestion()
    },
    toUserPage() {
      this.$router.push({
        path: '/userinfo',
        query: {
          userId: this.ownerId
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
  width: 50%;
  background-color: white;

  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  box-shadow: 0 0 5px 1px rgba(0, 0, 0, 0.3);
}

.top {
  display: flex;
  flex-direction: row;
}

.name-box {
  top: 0;
  bottom: 0;
  margin: auto;
  width: 80%;
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

.date-box {
  top: 0;
  bottom: 0;
  margin: auto;
  font-size: 15px;
  color: grey;
}

.middle {
  margin: 1% 0 0 0;
  border: solid 1px lightgrey;
  padding: 3%;
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

.divider-line {
  border: solid 1px lightgrey;
  margin: 2% 0 2% 0;
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
</style>