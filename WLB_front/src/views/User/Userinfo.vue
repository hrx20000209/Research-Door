<template>
  <div class="page" >
    <Navbar></Navbar>
    <div class="medium">
      <div class="intro">
        <span>
            <i class="el-icon-s-custom" style="font-size: 120px; color: grey;"></i>
        </span>
        <span class="userinfo">
           <div class="username">{{userInfo.userName}}</div>
           <div class="userinfo_item">Institution: {{userInfo.userInstitution}}</div>
           <div class="userinfo_item">Email: {{userInfo.userEmail}}</div>
        </span>
        <span class="stastics">
          <div class="stastics_item">Citations: <span class="line">---------------------------------</span> {{userInfo.userCiteCnt}}</div>
          <div class="stastics_item">Followers: <span class="line">---------------------------------</span>{{userInfo.userFollowerCnt}}</div>
          <div class="stastics_item">Research Interest Score: <span class="line">------------------</span>{{userInfo.userScore}}</div>
        </span>
      </div>
      <div class="menuBar">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal">
          <el-menu-item @click="router('/userinfo/profile')" index="/userinfo/profile">Profile</el-menu-item>
          <el-menu-item @click="router('/userinfo/research'); activeIndex='/userinfo/research'" index="/userinfo/research">Research</el-menu-item>
          <el-menu-item @click="router('/userinfo/following'); activeIndex='/userinfo/following'"  index="/userinfo/following">Following</el-menu-item>
        </el-menu>
        <el-button v-show="userId != userInfo.userId" style="margin-left: auto;" type="primary" @click="RRFormVisable=true">
          <i class="el-icon-tickets"></i>
          Require Reasearch
        </el-button>
        <el-button v-show="userId == userInfo.userId" style="margin-left: auto; background-color: rgb(0,204,187);" type="primary" @click="$router.push('/addAch')">
          <i class="el-icon-circle-plus-outline"></i>
          Add Reasearch
        </el-button>
      </div>
    </div>
      <div class="content">
        <router-view :key="$route.fullPath"></router-view>
      </div>
      <!-- require research -->
      <el-dialog title="Require Reasearch" 
      :visible.sync="RRFormVisable" 
      :close-on-click-modal=false
      >
      <el-form :model="RRForm">
        <el-form-item class="inputdeep" label="Title">
          <el-input v-model="RRForm.title" placeholder="Please enter the title of the research you required"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button plain style="color: rgb(0,204,187);" @click="canselRRform()">cansel</el-button>
        <el-button style="background-color: rgb(0,204,187);" type="primary" @click="requireRearch()">done</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
export default {
    components: {
      Navbar
    },
    created(){
      this.userId = sessionStorage.getItem("userId");
      this.checkUser()
    },
    data () {
      return {
        //???????????????????????????
        userInfo:{
          // userId: 7,
          // userName: 'ww',
          // userInstitution: 'Bhu',
          // introduction: 'hhhh',
          // userEmail: 'awd@buaa.edu.cn',
          // userCiteCnt: '20',
          // userFollowerCnt:'32',
          // userScore:'24',
        },
        userId: '', //?????????????????????userid,?????????Session???
        activeIndex: "/userinfo/profile",
        RRFormVisable: false,
        RRForm:{
          title: ''
        }
      }
    },
    methods:{
      async checkUser(){
        let checkUserParam={
          userId: this.$route.query.userId
        }
        const {data:res} = await this.$http.post("/account/checkUserById",checkUserParam);
        console.log(res)
        if(res.code==200){
          this.userInfo = res.data.userInfo;
        }
        else{
          this.$message.error(res.msg);
        }
      },
      canselRRform(){
        this.RRForm.title = '',
        this.RRFormVisable = false
      },
      async requireRearch(){
        let RRparam={
          userId: this.userInfo.userId,
          title: this.RRForm.title
        }
        console.log(RRparam)
        const {data:res} = await this.$http.post("/account/requireResearch",RRparam);
        if(res.code==200){
          this.$message({
            message: "Require Already Sent!",
            type: "success"
          })
        }
        else{
          this.$message.error(res.msg);
        }
      },
      router(route){
        this.activeIndex = route;
        this.$router.push({
          path: route,
          query:{
            userId: this.userInfo.userId
          }
        })
      }
    },
    watch:{
      $route: {
        handler(newVal, oldVal) {
          //??????newVal??????????????????????????????
          if (newVal != oldVal) {
            location.reload()
          }
        },
        deep: true
      }
  },

}
</script>

<style scoped>
.page{
  min-height: 100vh;
}
.medium{

}
.intro{
  height: 35vh;
  margin: 0 13vw;
  display: flex;
  align-items: center;
}
.menuBar{
  margin: 0 13vw;
  border-top: 2px solid #eeeeee;
  display: flex;
  align-items: center;
}
.content{
  background-color: #eeeeee;
  min-height: 50vh;
}
.userinfo{
  margin: 20px;
  text-align: left;
  width: 30vw;
}
.username{
  font-family: 'Times New Roman', Times, serif;
  font-size: 28px;
  margin-bottom: 18px;
}
.userinfo_item{
  color: grey;
  margin-bottom: 5px;
  font-style: italic;
}

.stastics{
  margin-left: 5vw;
  text-align: left;
}
.stastics_item{
  font-family: 'Roboto",Arial,sans-serif';
  font-size: 14px;
  margin-bottom: 20px;
  color: #333;
}
.line{
  color:#eeeeee;
}
.content{
  padding-top: 30px;
}
</style>
