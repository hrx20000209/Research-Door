<template>
    <div>
      <el-row>
        <div class="main-title">
          <i class="el-icon-folder-add" style="font-size:60px"></i>
          Add patent to your profile
        </div>
      </el-row>
      <el-row>
        <el-card class="box-card">
          <el-form
          ref="form"
          :model="formData"
          style="margin-left:80px;"
          :rules="rules"
          >
            <el-form-item prop="file">
              <template #label>
                <div style="font-size:20px; font-weight:600">File</div>
              </template>
              <el-upload
              class="upload"
              drag
              action="#"
              :auto-upload="false"
              :limit="1"
              :headers="headers"
              :file-list="fileList"
              style="float:left; margin-left:30px">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">Drag here or <em>click to add</em></div>
              </el-upload>
            </el-form-item>
            <el-form-item prop="title">
              <template #label>
                <span style="font-size:20px; font-weight:600">Title</span>
              </template>
              <el-input v-model="formData.title" placeholder="Please input the title"></el-input>
            </el-form-item>
            <el-form-item prop="authors">
              <template #label>
                <span style="font-size:20px; font-weight:600">Authors</span>
              </template>
              <el-input v-model="formData.authors" placeholder="Please input the names by order, divided by semicolons"></el-input>
            </el-form-item>
            <el-form-item prop="date">
              <template #label>
                <span style="font-size:20px; font-weight:600">Date</span>
              </template>
              <el-date-picker type="date" v-model="formData.date" placeholder="Please choose the date" style="width:300px"></el-date-picker>
            </el-form-item>
            <el-form-item prop="doi">
              <template #label>
                <div style="font-size:20px; font-weight:600">DOI</div>
              </template>
              <el-input v-model="formData.doi" placeholder="Please input the DOI"></el-input>
            </el-form-item>
            <el-form-item prop="abstract">
              <template #label>
                <div style="font-size:20px; font-weight:600">Abstract</div>
              </template>
              <el-input
              type="textarea"
              v-model="formData.abstract"
              placeholder="Abstract or description"
              rows="5"
              style="float:left; width: 480px"
              >
              </el-input>
            </el-form-item>
            <el-form-item prop="number">
              <template #label>
                <div style="font-size:20px; font-weight:600">Number</div>
              </template>
              <el-input v-model="formData.number" placeholder="Please input the number" style="width:380px"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="showBox = true" style="float:left; margin-left:220px">Submit</el-button>
              <el-dialog title="Notice" :visible.sync="showBox" :show-close="false" :close-on-click-modal="false" width="25%">
                <div style="font-size:15px">Are you sure to sumbit this patent?</div>
                <template #footer>
                  <el-button @click="showBox = false">cancel</el-button>
                  <el-button type="primary" @click="submit('form')">sure</el-button>
                </template>
              </el-dialog>
            </el-form-item>
          </el-form>
        </el-card>
      </el-row>
    </div>
  </template>
  
  <script>
  
  export default {
    name: "AddPatent",
    data(){
      return{
        form: null,
        formData: {
          title: '',
          authors: '',
          date: '',
          doi: '',
          abstract: '',
          number: '',
        },
        file: null,
        showBox: false,
        rules: {
          title: [
            { required: true, message: 'Please input the title', trigger: 'blur' },
          ],
          authors: [
            { required: true, message: 'Please input the names', trigger: 'blur' },
          ],
          date: [
            { type: 'date', required: true, message: 'Please choose the date', trigger: 'blur' }
          ],
          
        },
        headers: {'Content-Type': 'multipart/form-data'},
        fileList: [],
      }
    },
    components:{
      
    },
    methods:{
      submit(form){
        this.showBox = false;
        this.$refs[form].validate((valid) => {
          if (valid) {
            this.addAch();
          }
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
          userId: sessionStorage.getItem("userId"),
        }
        let param = new FormData();
        this.fileList.forEach(
          (val, index) => {
            param.append("file", val.raw);
          }
        );
        const {data:res} = await this.$http.post("/api/achievement/addAch", formData, param);
        if (res.status === 200){
          this.$message({
            message: 'Success',
            type: 'success',
          });
          router.push('/');
        }
        else {
          this.$message({
            message: 'Fail',
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