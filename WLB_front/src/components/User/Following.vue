<template>
  <div class="ProfileWrapper">
    <div class="leftside">
      <div class="introduction">
        <div class="introcard">
          <el-card class="box-card" shadow="hover" style="width: 80vw; margin: 2vw auto">
            <div slot="header" class="clearfix">
              <span>Following Works</span>
              <el-button v-show="$parent.userId == $parent.userInfo.userId"
                         style="float: right; padding: 3px 0; color: rgb(0,204,187);" type="text"
                         @click="editFormVisible=true">Add
              </el-button>
            </div>
            <h1>Research you follow</h1>
            <div class="" style="margin-left: 5vw; margin-top: 2vw; width: 12vw; float: left;">
              <el-menu default-active="1-2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
                <el-submenu index="1">
                  <template slot="title">
                    <i class="el-icon-files"></i>
                    <span slot="title">Research Items</span>
                  </template>
                  <el-menu-item-group>
                    <span slot="title">Your Research Items Taxonomies</span>
                    <el-menu-item index="1-1" disabled>Conference</el-menu-item>
                    <el-menu-item index="1-2">Article</el-menu-item>
                    <el-menu-item index="1-3" disabled>Data</el-menu-item>
                    <el-menu-item index="1-4" disabled>Research</el-menu-item>
                    <el-menu-item index="1-5" disabled>Presentation</el-menu-item>
                    <el-menu-item index="1-6" disabled>Poster</el-menu-item>
                  </el-menu-item-group>
                </el-submenu>
                <el-menu-item index="4">
                  <i class="el-icon-user-solid"></i>
                  <span slot="title">Topics</span>
                </el-menu-item>
              </el-menu>
            </div>
            <div style="width: 50vw; margin: 0 5vw 15vw 1vw;float: right; ">
              <div style="margin: 2vw auto;" v-if="followingWorkList.length != 0" v-for="work in followingWorkList">
                <ScholarDetails :author="work.author" style="width: 50vw;"
                                :title="work.title"
                                :description="work.description"
                                :read-cnt="work.doi"
                                :is-following="true">

                </ScholarDetails>
              </div>
              <div v-if="followingWorkList.length == 0">
                <el-empty description="Add your following first"></el-empty>
              </div>
            </div>
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

  </div>
</template>

<script>
import ScholarDetails from "../Scholar/ScholarDetails";
import Navbar from "../Navbar";

export default {
  name: "Following",
  components: {
    Navbar,
    ScholarDetails
  },
  created() {
  },
  mounted() {
    this.getFollowWorks();
  },
  data() {
    return {
      editFormVisible: false,
      inputIntroduction: '',
      followingWorkList: [],
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

      sendMsgFormVisable: false,
      msgTo: '',
    }
  }
  ,
  methods: {
    getintro() {
      this.inputIntroduction = this.$parent.userInfo.introduction;
    }
    ,
    cancelEditIntro() {
      this.getintro();
      this.editFormVisible = false;
    }
    ,
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
    }
    ,
    //得到正在关注
    async getFollowWorks() {
      console.log('getFollowWorks')
      let getFollowParam = {
        userId: this.$parent.userId,
      }
      const {data: res} = await this.$http.post("api/account/getFollowingAch", getFollowParam);
      console.log('getfollwingurl')
      console.log(res);
      this.followingWorkList = res.data

      // if (res.code == 200) {
      //   this.followingWorkList = res.data
      // } else
      //   this.$message.error(res.msg);
    },
    async getFollowings() {
      let getFollowParam = {
        userId: this.$parent.userId,
      }
      const {data: res} = await this.$http.post("/account/getFollowingUser", getFollowParam);
      console.log(res);
      if (res.code == 200) {
        this.followingList = res.data.followingList
      } else
        this.$message.error(res.msg);
    }
    ,
    //取消关注
    cancelMsgSend() {
      this.sendMsgFormVisable = false
    }
    ,
    confirmMsgSend() {
      this.sendMsgFormVisable = false
    }
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

.followings {
  width: 30vw;
}

.introcard {
  width: 60vw;
  text-align: left;
  margin-bottom: 20px;
}

#knowledgeGraph {
  width: 20vw;
  height: 20vw;
  margin: 5vh auto;
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
