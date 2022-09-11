<template>
  <div class="ProfileWrapper">
    <div class="leftside">
      <div class="introduction">
        <div class="introcard">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>About me</span>
              <el-button v-show="$parent.userId == $parent.userInfo.userId"
                         style="float: right; padding: 3px 0; color: rgb(0,204,187);" type="text"
                         @click="editFormVisible=true">edit
              </el-button>
            </div>
            <div class="introtext">
              {{ $parent.userInfo.introduction }}
            </div>
          </el-card>
        </div>
      </div>
<!--      <div class="introduction">
        <div class="introcard">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>Achievement</span>
            </div>
            <div class="introtext">
              Achievements
            </div>
          </el-card>
        </div>
      </div>-->
      <div class="introduction">
        <div class="introcard">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>Knowledge Graph</span>
            </div>
            <div style="font-family: 'Rockwell Condensed',serif; width: 20vw; margin-left: 2vw; float: left; margin-top: 5vh; font-style: italic; font-size: 3vw">Here to get your connection with other Innovators</div>
            <div id="knowledgeGraph">

            </div>
          </el-card>
        </div>
      </div>

    </div>
    <div class="rightside">
      <div class="followings">
          <div class="followingcard">
              <el-card class="box-card">
                <div slot="header" class="clearfix">
                  <span>followings</span>
                </div>
                <div class="followingwrap">
                  <div v-for="(item,index) in followingList.slice((currentPage1 - 1) * pageSize1, currentPage1 * pageSize1)"
                    :key="index" class="followingbox">
                    <span><i class="el-icon-s-custom" style="font-size: 60px; color: grey;"></i></span>
                    <span style="margin-left: 30px;">
                      <div class="followingUsername">{{item.userName}}</div>
                      <div class="followingInstitution">{{item.userInstitution}}</div>
                      <div class="followingInstitution">{{item.userEmail}}</div>
                    </span>
                    <span v-show="$parent.userInfo.userId == $parent.userId" style="margin-left: auto;">
                        <el-dropdown>
                            <span class="el-dropdown-link">
                                <i class="el-icon-more"></i>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item @click.native="msgTo = item.userName; sendMsgFormVisable=true;">send message</el-dropdown-item>
                                <el-dropdown-item @click.native="unfollowUser(item.userId)">unfollow</el-dropdown-item>
                                <el-dropdown-item @click.native="$router.push('/userinfo/profile?userId='+item.userId)">check userInfo</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </span>
                  </div>
                </div>
                <el-pagination
                  class="pagination"
                  layout="prev, pager, next"
                  @current-change="handleCurrentChange1"
                  :total= "followingList.length"
                  :page-size="pageSize1"
                  :pager-count="5"
                  :current-page.sync="currentPage1">
                </el-pagination>
              </el-card>
          </div>
        </div>
      <!-- follower -->
      <div class="followings">
            <div class="followingcard">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>followers</span>
                  </div>
                  <div class="followingwrap">
                    <div v-for="(item,index) in followerList.slice((currentPage2 - 1) * pageSize2, currentPage2 * pageSize2)"
                      :key="index" class="followingbox">
                      <span><i class="el-icon-s-custom" style="font-size: 60px; color: grey;"></i></span>
                      <span style="margin-left: 30px;">
                        <div class="followingUsername">{{item.userName}}</div>
                        <div class="followingInstitution">{{item.userInstitution}}</div>
                        <div class="followingInstitution">{{item.userEmail}}</div>
                      </span>
                      <span v-show="$parent.userInfo.userId == $parent.userId" style="margin-left: auto;">
                          <el-dropdown>
                              <span class="el-dropdown-link">
                                  <i class="el-icon-more"></i>
                              </span>
                              <el-dropdown-menu slot="dropdown">
                                  <el-dropdown-item @click.native="msgTo = item.userName; sendMsgFormVisable=true;">send message</el-dropdown-item>
                                  <el-dropdown-item @click.native="followUser(item.userId)">follow</el-dropdown-item>
                                  <el-dropdown-item @click.native="$router.push('/userinfo/profile?userId='+item.userId)">check userInfo</el-dropdown-item>
                              </el-dropdown-menu>
                          </el-dropdown>
                      </span>
                    </div>
                  </div>
                  <el-pagination
                    class="pagination"
                    layout="prev, pager, next"
                    @current-change="handleCurrentChange2"
                    :total= "followerList.length"
                    :page-size="pageSize2"
                    :pager-count="5"
                    :current-page.sync="currentPage2">
                  </el-pagination>
                </el-card>
            </div>
          </div>
    </div>
    <el-dialog title="edit introduction" :visible.sync="editFormVisible">
      <el-input v-model="inputIntroduction" autocomplete="off"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button plain style="color: rgb(0,204,187);" @click="cancelEditIntro">cancel</el-button>
        <el-button style="background-color: rgb(0,204,187);" type="primary" @click="confirmEditIntro">confirm
        </el-button>
      </div>
    </el-dialog>
    <message-send
        :sendMsgFormVisable='sendMsgFormVisable'
        :msgTo='msgTo'
        @cansel="cancelMsgSend()"
        @confirm="confirmMsgSend()"
    ></message-send>
  </div>
</template>

<script>
import MessageSend from '@/components/User/MessageSend.vue'
import * as echarts from 'echarts';

export default {
  components: {
    MessageSend
  },
  created() {
    // setTimeout(getintro(),500);
    this.getFollow();
  },
  mounted() {
    // let form = {
    //   userId: this.userId,
    // }
    // const {data:res} = this.$http.post("/question/followQuestion", form);
    // if (res.code === 200) {
    //   this.$message({
    //     message: "Follow successfully!",
    //     type: "success"
    //   })
    // }
    // const res = this.$http.post("/question/getGraphByID", {
    //     userId: this.userId,
    //   });
    var Graph = require('../../assets/json/show.json')
    // Graph = res.data;
    var chartDom = document.getElementById('knowledgeGraph');
    var myChart = echarts.init(chartDom);
    console.log(myChart)
    var option;
    console.log('in mount')
    option = {
      tooltip: {},
      legend: [
        {
          data: Graph.categories.map(function (a) {
            return a.name;
          })
        }
      ],
      series: [
        {
          name: 'Knowledge Graph',
          type: 'graph',
          layout: 'none',
          data: Graph.nodes,
          links: Graph.links,
          categories: Graph.categories,
          roam: true,
          label: {
            show: true,
            position: 'right',
            formatter: '{b}'
          },
          labelLayout: {
            hideOverlap: true
          },
          scaleLimit: {
            min: 0.4,
            max: 2
          },
          lineStyle: {
            color: 'source',
            curveness: 0.3
          }
        }
      ]
    };
    myChart.setOption(option);
    option && myChart.setOption(option);
  },
  data() {
    return {
      editFormVisible: false,
      inputIntroduction: '',
      followingList: [
        {
          userId: 1,
          userName: "Yeqi Gao",
          userInstitution: "BUAA",
          userEmail: "123@buaa.edu.cn",
          follow: true,

        },
        {
          userId: 7,
          userName: "liuzy",
          userInstitution: "BUAA",
          userEmail: "72@buaa.edu.cn",
          follow: true,
        },
        {
          userId: 9,
          userName: "wzh",
          userInstitution: "BUAA",
          userEmail: "wzh@buaa.edu.cn",
          follow: true,
        }
      ],
      followerList: [
            {
                userId: 1,
                userName: "Yeqi Gao",
                userInstitution: "BUAA",
                userEmail: "123@buaa.edu.cn",
                follow: true,

            },
            {
                userId: 7,
                userName: "liuzy",
                userInstitution: "BUAA",
                userEmail: "72@buaa.edu.cn",
                follow: true,
            }],
      sendMsgFormVisable: false,
      msgTo: '',
      currentPage1: 1,
      currentPage2: 1,
      pageSize1: 3,
      pageSize2: 3
    }
  },
  methods: {
    getintro() {
      this.inputIntroduction = this.$parent.userInfo.introduction;
    },
    cancelEditIntro() {
      this.getintro();
      this.editFormVisible = false;
    },
    async confirmEditIntro() {
      let editForm = {
        userId: this.$parent.userId,
        introduction: this.inputIntroduction
      }
      const {data: res} = await this.$http.post("/account/editIntroduction", editForm);
      if (res.code == 200) {
        await this.$parent.checkUser();
        this.getintro();
        this.editFormVisible = false;
      } else
        this.$message.error(res.msg);
    },
    //得到正在关注
    async getFollow() {
      let getFollowParam = {
        userId: this.$route.query.userId,
      }

      const {data: res} = await this.$http.post("/account/getFollowingUser", getFollowParam);
      console.log(res);
      if (res.code == 200) {
        this.followingList = res.data.followingList
        this.followerList=res.data.followerList
      } else
        this.$message.error(res.msg);
    },
    //关注
    async followUser(userToFollowId){
          let followParam={
            userId: this.$parent.userId,
            userToFollowId: userToFollowId,
          }
          const {data:res} = await this.$http.post("/account/followUser",followParam);
          if(res.code == '200'){
            this.$message({
              message: 'Successfully Followed',
              type: 'success'
            });
            this.getFollow();
          }
          else
            this.$message.error(res.msg);
      },
    //取消关注
    async unfollowUser(userToUnfollowId) {
      let unfollowParam = {
        userId: this.$parent.userId,
        userToUnfollowId: userToUnfollowId,
      }
      console.log("profile" + unfollowParam)
      const {data: res} = await this.$http.post("/account/unfollowUser", unfollowParam);
      if (res.code == '200') {
        this.$message({
          message: 'Successfully unFollowed',
          type: 'success'
        });
        this.getFollow();
      } else
        this.$message.error(res.msg);
    },
    cancelMsgSend() {
      this.sendMsgFormVisable = false
    },
    confirmMsgSend() {
      this.sendMsgFormVisable = false
    },
    handleCurrentChange1 (currentPage1) {
      this.currentPage1 = currentPage1;
    },
    handleCurrentChange2 (currentPage2) {
      this.currentPage2 = currentPage2;
    },

  }
}
</script>

<style scoped>
.ProfileWrapper {
  display: flex;
}

.introduction {
  width: 70vw;
  display: flex;
  justify-content: center;
}

.introtext {
  width: 90%;
  padding: 20px;
  word-break: break-all;
  color: gray;
  font-style: italic;
}

.rightside{
  margin-bottom: 5vh;
}
.followings {
  width: 30vw;
  margin-bottom: 5vh;
}
.introcard {
  width: 60vw;
  text-align: left;
  margin-bottom: 20px;
}

#knowledgeGraph {
  width: 45vw;
  height: 25vw;
  margin: 5vh 20vw;
}
#knowledgeGraph canvas {
  width: 80%;
  height: 80%;
}

.followingcard {
  width: 25vw;
  min-height: 40vh;
  text-align: left;
}

.followingbox {
  display: flex;
  padding: 20px 0;
  border-bottom: 1px solid grey;
}

.followingUsername {
  font-family: 'Times New Roman', Times, serif;
  font-size: 18px;
  margin-bottom: 5px;
}

.followingInstitution {
  color: grey;
  font-size: 14px;
}
</style>
