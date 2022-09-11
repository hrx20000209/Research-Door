<template>
  <div class="ProfileWrapper">
    <div class="leftside" style="margin-left: 20vw; width: 12vw">
      <el-menu default-active="1-1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" >
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-files"></i>
            <span slot="title">Research Items</span>
          </template>
          <el-menu-item-group>
            <span slot="title">Your Research Items Taxonomies</span>
            <el-menu-item index="1-1">Conference</el-menu-item>
            <el-menu-item index="1-2">Article</el-menu-item>
            <el-menu-item index="1-3">Data</el-menu-item>
            <el-menu-item index="1-4">Research</el-menu-item>
            <el-menu-item index="1-5">Presentation</el-menu-item>
            <el-menu-item index="1-6">Poster</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-menu-item index="2">
          <i class="el-icon-question"></i>
          <span slot="title">Questions</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-data-analysis"></i>
          <span slot="title">Answers</span>
        </el-menu-item>
        <el-menu-item index="4">
          <i class="el-icon-user-solid"></i>
          <span slot="title">Authorship</span>
        </el-menu-item>
      </el-menu>
    </div>
    <div class="rightside" style="margin-right: 20vw; margin-left: 10vw; width: 40vw; float: right">
      <el-card shadow="hover" style="height: 20vw; border-radius: 1vw">
        <el-upload
            class=""
            drag
            action="https://jsonplaceholder.typicode.com/posts/"
            style="width: 35vw !important; height: 18vw !important;"
            multiple>
<!--          <h1>Your notes</h1>-->
          <i class="el-icon-upload" ></i>
          <div class="el-upload__text">Add your publications to increase the visibility of your research. Once you've added them, your publications will be listed here.
            Add a publication <div> <em>Upload</em></div></div>
        </el-upload>
      </el-card>
    </div>

  </div>
</template>

<script>
export default {
  name: "Research",
  created() {
    this.getFollow();
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
      sendMsgFormVisable: false,
      msgTo: '',
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
        userId: this.$parent.userId,
      }
      const {data: res} = await this.$http.post("/account/getFollowingUser", getFollowParam);
      console.log(res);
      if (res.code == 200) {
        this.followingList = res.data.followingList
      } else
        this.$message.error(res.msg);
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
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
<style>

</style>
