<template>
  <div class="main-body">
    <div class="top">
      <div class="ach-info">
        <el-container>
          <div class="left-info">
            <div class="first-line">
              <div class="green-box">
                {{ info.type }}
              </div>
              <div class="white-box" v-if="info.isAvailable===1">
                File available
              </div>
              <div class="white-box" v-if="info.isAuthor===1">
                Authenticated
              </div>
            </div>
            <div class="title">
              {{ info.title }}
            </div>
            <div class="date" v-if="info.type==='Article'&&info.journal!==''">
              {{ info.date }} · {{ info.journal }} {{ info.issue }}({{ info.volume }}):{{ info.page }}
            </div>
            <div class="date" v-else>
              {{ info.date }}
            </div>
            <div class="doi" v-if="info.type==='Article'&&info.doi!==''">
              DOI: {{ info.doi }}
            </div>
            <div class="institution" v-if="info.type==='Technical Report'&&info.institution!==''">
              Institution: {{info.institution}}
            </div>
            <div class="authors">
              <div v-for="(item, index) in info.author" :key="index">
                <div class="author-name" v-if="item.userId!==0" @click="$router.push('/userinfo/profile?userId='+item.userId)">
                  {{ item.name }} &nbsp
                </div>
                <div v-else>
                  {{ item.name }} &nbsp
                </div>
              </div>
            </div>
          </div>
          <div class="right-info">
            <div class="citations">
              Citations: {{info.citeCnt}}
            </div>
            <div class="recommendations">
              Recommendations: {{info.recommendCnt}}
            </div>
            <div class="reads">
              Reads: {{info.readCnt}}
            </div>
            <div style="margin-top:4%">
              <el-button type="text" v-if="info.isAvailable===1" @click="download">
                <div class="text-btn">Download</div>
              </el-button>
              <el-button type="text" v-else @click="request">
                <div class="text-btn">Request File</div>
              </el-button>
            </div>
            <div>
              <el-button type="text" v-if="info.isAuthor===0" @click="showBox = true" style="margin-right:4%">
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
                <div class="text-btn" v-if="info.isFollowed===0">
                  Follow
                </div>
                <div class="text-btn" v-else>
                    Following
                </div>
              </el-button>
            </div>
          </div>
        </el-container>
        <div style="margin-top: 4%;">
          <el-divider></el-divider>
        </div>
      </div>
    </div>
    <div class="body">
      <div style="margin: 5%;"></div>
      <el-container>
        <div class="abstract">
          <el-card>
            <template #header>
              <div style="font-size:18px;">Abstract & Description</div>
            </template>
            <div style="text-align:left">{{ info.abstract }}</div>
          </el-card>
        </div>
        <div class="ad">
          <el-carousel trigger="click">
            <el-carousel-item>
              <img :src="ad1" />
            </el-carousel-item>
            <el-carousel-item>
              <img :src="ad2" />
            </el-carousel-item>
            <el-carousel-item>
              <img :src="ad3" />
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-container>
    </div>
  </div>
</template>
  
<script>
export default {
  name: "Search",
  data() {
    return {
      info: {
        
      },
      ad1: require("../../assets/images/Achievement/ad1.png"),
      ad2: require("../../assets/images/Achievement/ad2.png"),
      ad3: require("../../assets/images/Achievement/ad3.png"),
      showBox: false,
      name: '',
    }
  },
  created(){
    this.getDetail();
  },
  methods: {
    async getDetail(){
      let param = {
        id: this.$route.query.id,
        userId: sessionStorage.getItem("userId"),
      };
      console.log('param.id: ' + param.id);
      console.log('param.userId: ' + param.userId);
      const {data:res} = await this.$http.post("/api/achievement/getDetail", param);
      if (res.status === 200){
        this.info = res.data.info;
      }
      else {
        this.$message({
          message: 'Fail',
          type: 'error',
        });
      }
    },
    //下载成果
    download(){
      this.downloadAch();
    },

    async downloadAch(){
      let id = this.$route.query.id;
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
        id: this.$route.query.id,
        userId: sessionStorage.getItem("userId"),
      };
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
      if(this.info.isFollowed == 0) this.followAch();
      else this.cancelFollow();
    },

    async followAch(){
      let param = {
        id: this.$route.query.id,
        userId: sessionStorage.getItem("userId"),
      };
      const {data:res} = await this.$http.post("/api/achievement/followAch", param);
      if(res.status === 200){
        this.info.isFollowed = 1;
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
        id: this.$route.query.id,
        userId: sessionStorage.getItem("userId"),
      };
      const {data:res} = await this.$http.post("/api/achievement/cancelFollow", param);
      if(res.status === 200){
        this.info.isFollowed = 0;
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
        id: this.$route.query.id,
        userId: sessionStorage.getItem("userId"),
        name: this.name,
      };
      const {data:res} = await this.$http.post("/api/achievement/authentication", param);
      if(res.status === 200){
        this.info.isAuthor = 1;
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
  }
}
</script>
  
<style scoped>
.main-body {
  font-family: "Roboto",Arial,sans-serif;
  background-color: #f4f4f4;
  height: 100%;
}
.top {
  background-color: white;
  display: flex;
  flex-direction: column;
}
.ach-info {
  width: 65%;
  margin: auto;
}
.left-info {
  width: 60%;
}
.first-line {
  display: flex;
  flex-direction: row;
  margin: 4% 0 2% 0;
}
.green-box {
  background-color: #C5E8E5;
  color: #007478;
  text-align: left;
  height: 25px;
  width: 50px;
  padding-top: 6px;
  padding-left: 6px;
}
.white-box {
  margin-left: 6px;
  border: solid 1px #007478;
  color: #007478;
  text-align: left;
  height: 23px;
  width: 100px;
  padding-top: 6px;
  padding-left: 6px;
}
.title {
  font-size: 20px;
  text-align: left;
  font-weight: 600;
}
.date {
  font-size: 16px;
  color: gray;
  text-align: left;
  margin: 2% 0 1% 0;
}
.doi {
  font-size: 16px;
  color: gray;
  text-align: left;
}
.institution {
  font-size: 16px;
  color: gray;
  text-align: left;
}
.authors {
  font-size: 17px;
  text-align: left;
  margin-top: 2%;
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
.right-info {
  text-align: left;
  padding: 7% 0 0 8%;
  width: 30%
}
.text-btn {
  font-size: 17px;
  color: #007478;
}
.text-btn:hover {
  cursor: pointer;
  text-decoration: underline;
}
.citations {
  font-size: 16px;
  margin-bottom: 2%;
}
.recommendations {
  font-size: 16px;
  margin-bottom: 2%;
}
.reads {
  font-size: 16px;
}
.top-bottom {
  width: 65%;
  margin: auto;
}
.body {
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  margin: auto;
  width: 65%;
}
.abstract {
  width: 60%;
}
.ad {
  width: 35%;
  margin-left: 5%;
}
</style>