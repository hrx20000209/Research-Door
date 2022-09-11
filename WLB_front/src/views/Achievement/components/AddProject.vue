<template>
    <div>
      <el-row>
        <div class="main-title">
          <i class="el-icon-folder-add" style="font-size:60px"></i>
          请填写您创建的项目信息
        </div>
      </el-row>
      <el-row>
        <el-card class="box-card">
            <div>
              <div style="display:inline-block; font-size: medium; vertical-align: middle;">请输入项目名称</div>
              <div style="display:inline-block; font-size: medium; vertical-align: middle;">
                <el-input v-model="projname" type="text" placeholder="请输入内容" maxlength="10" show-word-limit></el-input>
              </div>
            </div>
            <br>
            <div>
              <div style="display:inline-block; font-size: medium; vertical-align: middle;">请输入项目描述</div>
              <div style="display:inline-block; font-size: medium; vertical-align: middle;">
                <el-input v-model="projdescription" type="text" placeholder="请输入内容" maxlength="50" show-word-limit></el-input>
              </div>
            </div>
            <br>
            <div style="display:inline-block; font-size: medium; vertical-align: middle;"><el-button type="primary" @click="submit">上传</el-button></div>
        </el-card>
      </el-row>
    </div>
  </template>
  
  <script>
  
  export default {
    name: "AddProject",
    data(){
      return{
        userid:0,
        projname:'',
        projdescription:'',
      }
    },
    methods:{
      async submit(){
        await this.$http
                .post("/api/createProj", {
                projname:this.projname,
                projectdescription:this.projdescription,
                projuserid:this.userid
                })
                .then((res) => {
                    this.$message.success("上传成功");
                    
                    console.log(res);
                })
                .catch((err) => {
                this.$message.error("网络错误,请稍后再试!");
                });
      },

      async addAch(){
        let formData = {
          type: 2,
          title: this.formData.title,
          authors: this.formData.authors,
          date: this.formData.date,
          doi: this.formData.doi,
          description: this.formData.abstract,
          number: this.formData.number,
          userId: 1,
        }
        const {data:res} = await this.$http.post("/api/achievement/addAch", formData);
        if (res.status === 200){
          this.$message({
            message: '上传成功',
            type: 'success',
          });
          router.push('/');
        }
        else {
          this.$message({
            message: '上传失败',
            type: 'error',
          });
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .main-title {
    font-size: 30px;
    font-weight: 1000;
    margin-top: 30px;
  }
  .el-card {
    width: 700px;
    margin-top: 40px;
    margin-left: 130px;
  }
  .el-input {
      width: 400px;
      float: left;
      margin-left: 30px;
  }
  </style>