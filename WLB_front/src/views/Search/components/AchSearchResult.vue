<template>
  <div class="main">
    <div style="padding: 4%">
      <div class="title" @click="checkDetail">
        {{ title }}
      </div>
      <div class="second-line">
        <div class="green-box">
          {{ type }}
        </div>
        <div class="white-box" v-if="isAvailable===1">
          File available
        </div>
        <div class="white-box" v-if="isAuthor===1">
          Authenticated
        </div>
        <div class="date-box">
          {{ date }}
        </div>
      </div>
      <div class="third-line">
        <div v-for="(item, index) in author" :key="index">
          <div class="author-name" v-if="item.userId!==0" @click="$router.push('/userinfo/profile?userId='+item.userId)">
            {{ item.name }} &nbsp
          </div>
          <div v-else>
            {{ item.name }} &nbsp
          </div>
        </div>
      </div>
      <div class="abstract-box">
        {{ abstract }}
      </div>
      <div class="data-box">
        <div v-if="recommendCnt!==0">
          {{ recommendCnt }} Recommendations ·&nbsp
        </div>
        <div v-if="citeCnt!==0">
          {{ citeCnt }} Citations ·&nbsp
        </div>
        <div>
          {{ readCnt }} Reads
        </div>
      </div>
      <div class="bottom">
        <span>
          <el-button type="primary" plain v-if="isAvailable===1" @click="download">Download</el-button>
          <el-button type="primary" plain v-else @click="request">Request file</el-button>
        </span>
        <div style="width: 50%" v-if="isAuthor===0"></div>
        <div style="width: 65%" v-else></div>
        <div>
          <el-button type="text" v-if="isAuthor===0" @click="showBox = true" style="margin-right:11px">
            <div class="text-btn">
              Authenticate
            </div>
          </el-button>
          <el-dialog title="Authenticate" :visible.sync="showBox" :show-close="false" :close-on-click-modal="false" width="25%">
            <div style="font-size:15px">
              <el-input v-model="name" placeholder="Please input the author you are authenticating"></el-input>
            </div>
            <template #footer>
              <el-button @click="showBox = false">cancel</el-button>
              <el-button type="primary" @click="authenticate">sure</el-button>
            </template>
          </el-dialog>
          <el-button type="text" @click="follow">
            <div class="text-btn" v-if="isFollowed===0">
              Follow
            </div>
            <div class="text-btn" v-else>
              Following
            </div>
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "QuestionSearchResult",
  props: ["id", "title", "type", "isAvailable", "date", "author", "abstract", "readCnt", "citeCnt", "recommendCnt", "isFollowed", "isAuthor"],
  data() {
    return {
      showBox: false,
      name: '',
      showShare: false,
    }
  },
  methods: {
    //下载成果
    download(){
      this.downloadAch();
    },

    async downloadAch(){
      let id = this.id;
      const {data:res} = await this.$http.post("/api/achievement/downloadAch", id);
      if (res.status === 200){
        window.open(res.data.url);
      }
      else {
        this.$message({
          message: 'Fail',
          type: 'error',
        });
      }
    },

    //请求全文
    request(){
      this.requestAch();
    },

    async requestAch(){
      let param = {
        id: this.id,
        userId: sessionStorage.getItem("userId"),
      };
      console.log("param.id: " + param.id);
      const {data:res} = await this.$http.post("/api/achievement/requestAch", param);
      if(res.status === 200){
        this.$message({
          message: 'Success',
          type: 'success',
        });
      }
      else {
        this.$message({
          message: 'Fail',
          type: 'error',
        });
      }
    },

    //Follow/取消Follow成果
    follow(){
      if(this.isFollowed == 0) this.followAch();
      else this.cancelFollow();
    },

    async followAch(){
      let param = {
        id: this.id,
        userId: sessionStorage.getItem("userId"),
      };
      const {data:res} = await this.$http.post("/api/achievement/followAch", param);
      if(res.status === 200){
        this.isFollowed = 1;
        this.$message({
          message: 'Success',
          type: 'success',
        });
      }
      else {
        this.$message({
          message: 'Fail',
          type: 'error',
        });
      }
    },

    async cancelFollow(){
      let param = {
        id: this.id,
        userId: sessionStorage.getItem("userId"),
      };
      const {data:res} = await this.$http.post("/api/achievement/cancelFollow", param);
      if(res.status === 200){
        this.isFollowed = 0;
        this.$message({
          message: 'Success',
          type: 'success',
        });
      }
      else {
        this.$message({
          message: 'Fail',
          type: 'error',
        });
      }
    },

    //认证成果
    authenticate(){
      this.showBox = false;
      this.authentication();
    },

    async authentication(){
      let param = {
        id: this.id,
        userId: sessionStorage.getItem("userId"),
        name: this.name,
      };
      console.log("param.id: " + this.id);
      console.log("param.userId: " + param.userId);
      console.log("param.name: " + param.name);
      const {data:res} = await this.$http.post("/api/achievement/authentication", param);
      if(res.status === 200){
        this.isAuthor = 1;
        this.$message({
          message: 'Success',
          type: 'success',
        });
      }
      else {
        this.$message({
          message: 'Fail',
          type: 'error',
        });
      }
    },

    //跳转到detail
    checkDetail() {
      this.$router.push({
        path: '/achDetail',
        query: {
          id: this.id,
        }
      })
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
  font-weight:bold;
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
.white-box {
  margin-left: 6px;
  border: solid 1px #007478;
  color: #007478;
  text-align: left;
  padding: 1%;
}
.date-box {
  padding: 1%;
  color: grey;
}
.third-line {
  text-align: left;
  margin-bottom: 1%;
  display: flex;
  flex-direction: row;
}
.author-name {
  color: black;
}
.author-name:hover {
  cursor: pointer;
  text-decoration: underline;
}
.abstract-box {
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
.text-btn {
  font-size: 17px;
  color: black;
}
.text-btn:hover {
  cursor: pointer;
  text-decoration: underline;
}
</style>