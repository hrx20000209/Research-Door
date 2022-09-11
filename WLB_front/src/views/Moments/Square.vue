<template>
    <div>
    <navbar></navbar>
    <div class="whole">

        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-menu-item index="1">Achievement Square</el-menu-item>
        <el-menu-item index="2">Project Square</el-menu-item>
        </el-menu>

        <div class="line"></div>
        <br>

        <div v-if="type == 1">
            <span style="display:inline-block"><el-input v-model="smg_keyword" placeholder="按关键词检索" clearable ></el-input></span>
            &nbsp;
            <span style="display:inline-block"><el-button @click="searchsmg_by_keyword" icon="el-icon-search" circle></el-button></span>
        </div>

        <div v-if="type == 2">
            <span style="display:inline-block"><el-input v-model="proj_keyword" placeholder="按关键词检索" clearable ></el-input></span>
            &nbsp;
            <span style="display:inline-block"><el-button @click="searchproj_by_keyword" icon="el-icon-search" circle></el-button></span>
        </div>



        <div class="search_list">
            <!-- 成果广场 -->
        <el-card v-for="(item,index) in current_smg" class="list_card" shadow="hover"
                 style="border-radius: 1vw; border: none; margin-bottom: 1.5vw " v-if="type == 1">
          <div slot="header" class="clearfix">
            <div class="abstract" style="font-weight: 1000; font-size: 18px">{{ item.smgdescription }}</div>
            <span style="font-weight: 900; float: right;bottom: 2vh; position: relative">Publish : {{
                item.smgdate.slice(0, 10)
              }} </span>

            <!--          <span class="card_foot">-->
            <!---->
            <!--            <span>点赞数：{{ item.smglikenum }}</span>-->
            <!--          </span>-->
          </div>
          <div style="float: left;">
                <div class="title">
            <span style="vertical-align:middle;"><el-avatar :size="40" :src="circle" shape="circle"
                                                            alt="暂无头像"></el-avatar></span>
                    &nbsp;
                    <span style="vertical-align:middle;">{{item.smgowners}}</span>
                </div>

          </div>

                <br>
          <div style="margin: 10px 0; float: right; position: relative; bottom: 4vh">
            <el-button class="button1" @click="seeDetail(index)">
                      <i class="el-icon-reading"></i>详情
            </el-button>
                    &nbsp;
            <el-button class="button1" @click="likesmg(index)" v-if="item.smgflag == '0'">
              <i class="el-icon-s-opportunity"></i>Like
            </el-button>
            <el-button class="button1" v-if="item.smgflag == '1'">
              Like: {{ item.smglikenum }}
            </el-button>

                </div>
                <div>
                    <el-dialog
                    title="成果详情"
                    :visible.sync="dialogVisible"
                    width="30%"
                    :before-close="handleClose">
                    <el-descriptions title="成果详情">
                <el-descriptions-item label="作者">{{ d_smgowners }}</el-descriptions-item>
                        <el-descriptions-item label="点赞者">{{d_smglike}}</el-descriptions-item>
                        <el-descriptions-item label="点赞数">{{d_smglikenum}}</el-descriptions-item>
                <el-descriptions-item label="成果描述">{{ d_smgdescription }}</el-descriptions-item>
                <el-descriptions-item label="成果创建时间">{{ d_smgdate }}</el-descriptions-item>
                    </el-descriptions>
                  </el-dialog>
                </div>
        </el-card>

            <!-- 项目广场 -->
        <el-card v-for="(item,index) in current_proj" class="list_card" shadow="hover"
                 style="border-radius: 1vw; border: none; margin-bottom: 1.5vw " v-if="type == 2">
                <div class="title">
                    {{item.projname}}
                </div>
          <div slot="header" class="clearfix">
            <div class="abstract" style="font-weight: 1000; font-size: 18px">{{ item.projdescription }}</div>
            <span style="font-weight: 900; float: right;bottom: 2vh; position: relative">Publish : {{
                item.projdate.slice(0, 10)
              }} </span>

            <!--          <span class="card_foot">-->
            <!---->
            <!--            <span>点赞数：{{ item.smglikenum }}</span>-->
            <!--          </span>-->
          </div>
                <br>
                <div style="margin: 10px 0;">
                    <div class="button" v-if="item.projectflag == '0'"  @click="joinProject(index)">
                      <i class="el-icon-folder-add"></i>
                    </div>
            <div style="height:10px;font-size: 13px;color:deepskyblue;" v-if="item.projectflag == '1'">Joined</div>
                </div>
                <div style="margin: 10px 0">
            <div style="height:10px;font-size: 13px;color:deepskyblue;" v-if="item.projectflag == '0'">Come and join!</div>
                    <div style="height:10px;font-size: 13px;color:deepskyblue;" v-if="item.projectflag == '1'"></div>
                    <div class="card_foot">
              <span>Partner {{ item.projectjoiner }}</span>
                    </div>
                </div>

        </el-card>

        </div>
        <div style="height: 10px; border-top: 1px solid #ccc"></div>
        <div v-if="type == 1" style="text-align: center; padding: 20px 0">
            <el-pagination
              background
              layout="prev, pager, next"
              :total="smgtotal"
              :page-size="10"
              :pager-count="5"
              :current-page="smgpage"
              @current-change="changesmg"
            >
            </el-pagination>
        </div>
        <div v-if="type == 2" style="text-align: center; padding: 20px 0">
            <el-pagination
              background
              layout="prev, pager, next"
              :total="projtotal"
              :page-size="10"
              :pager-count="5"
              :current-page="projpage"
              @current-change="changeproj"
            >
            </el-pagination>
        </div>
    </div>
    </div>
</template>

<script>
    //import Loading from "../User/Loading.vue"
import Navbar from "../../components/Navbar";

    export default {
  components: {Navbar},
        created(){
        //传userid，
        this.userid=sessionStorage.getItem("userId");
        //传入所有动态
        this.proj_keyword="";
        this.searchproj_by_keyword();
        this.smg_keyword="";
        this.searchsmg_by_keyword();

        //传入所有项目


        this.change_current_proj(1);
        this.change_current_smg(1);
        },
        data(){
            return{
                // detailitem:{
                //     d_id:"",
                //     d_owners:"",
                //     d_description:"",

                // },
      circle: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
                smg_keyword:"",
                proj_keyword:"",
                userid:14,
                activeIndex: '1',
                type:1,
                current_smg:[],
                current_proj:[],
                projpage:1,
                smgpage:1,
                projtotal:11,
                smgtotal:12,
                project:[

                ],
                statemsg:[

                ],
                dialogVisible:false,
                d_smgowners:'',
                d_smglike:'',
                d_smglikenum:'',
                d_smgdescription:'',
                d_smgdate:'',

            }

        },
        methods: {
            handleClose(done) {
                this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {});
            },
            //根据关键词检索动态
            async searchsmg_by_keyword(){
                this.statemsg.splice(0,this.statemsg.length);
                await this.$http
                .post("/api/selectSmg", {
                keyword:this.smg_keyword,
                userid:this.userid
                })
                .then((res) => {
            // this.$message.success(res.data.message);
                    this.smgtotal = res.data.data.length;
                    for (var i = 0; i < res.data.data.length; i++) {
                    var a = {
                        smgid:"",
                        smgowners:"",
                        smgtype:"",
                        smgrelationid:"",
                        smglike:"",
                        smglikenum:"",
                        smgdescription:"",
                        smgdate:"",
                        smgflag:"",
                    };
                    a.smgid = res.data.data[i].smgid;
                    a.smgowners = res.data.data[i].smgowners;
                    a.smgtype = res.data.data[i].smgtype;
                    a.smgrelationid = res.data.data[i].smgrelationid;
                    a.smglike = res.data.data[i].smglike.replace(","," ");
                    a.smglikenum = res.data.data[i].smglikenum;
                    a.smgdescription = res.data.data[i].smgdescription;
                    a.smgdate = res.data.data[i].smgdate.substring(0,10);
                    a.smgflag = res.data.data[i].smgflag;
                    this.statemsg.push(a);
                    }
                    this.statemsg.reverse();
                    console.log(res);
                })
                .catch((err) => {
                this.$message.error("网络错误,请稍后再试!");
                });
                this.change_current_smg(1);
            },
            async searchproj_by_keyword(){
                this.project.splice(0,this.project.length);
                await this.$http
                .post("/api/selectProj", {
                keyword:this.proj_keyword,
                userid:this.userid
                })
                .then((res) => {
            // this.$message.success(res.data.message);
                    this.projtotal = res.data.data.length;
                    for (var j = 0; j < res.data.data.length; j++) {
                    var b = {
                        projid:"",
                        projuserid:"",
                        projname:"",
                        projdate:"",
                        projdescription:"",
                        projectjoiner:"",
                        projectflag:"",
                    };
                    b.projid = res.data.data[j].projid;
                    b.projuserid = res.data.data[j].projuserid;
                    b.projname = res.data.data[j].projname;
                    b.projdate = res.data.data[j].projdate.substring(0,10);
                    b.projdescription = res.data.data[j].projdescription;
                    b.projectjoiner = res.data.data[j].projectjoiner.replace(","," ");
                    b.projectflag = res.data.data[j].projectflag;
                    this.project.push(b);
                    }
                    this.project.reverse();
                    console.log(res);
                })
                .catch((err) => {
                    this.$message.error("网络错误,请稍后再试!");
                });
                this.change_current_proj(1);
            },

            //分页器
            changeproj(val){
                this.projpage=val;
                this.change_current_proj(this.projpage);
            },
            changesmg(val){
                this.smgpage=val;
                this.change_current_smg(this.smgpage);
            },
            change_current_proj(val){


                this.current_proj=[];
                if(val==1){
                    if(this.projtotal<=10){

                    for(var k=0;k<this.projtotal;k++){
                        var c={
                        projid:'',
                        projuserid:'',
                        projname:'',
                        projdate:'',
                        projdescription:'',
                        projectjoiner:'',
                        projectflag:''
                    };
                        c.projid=this.project[k].projid;
                        c.projuserid=this.project[k].projuserid;
                        c.projname=this.project[k].projname;
                        c.projdate=this.project[k].projdate;
                        c.projdescription=this.project[k].projdescription;
                        c.projectjoiner=this.project[k].projectjoiner;
                        c.projectflag=this.project[k].projectflag;
                        this.current_proj.push(c)
                    }
                    }else{

                    for(var l=0;l<10;l++){
                        var d={
                        projid:'',
                        projuserid:'',
                        projname:'',
                        projdate:'',
                        projdescription:'',
                        projectjoiner:'',
                        projectflag:''
                    };
                        d.projid=this.project[l].projid;
                        d.projuserid=this.project[l].projuserid;
                        d.projname=this.project[l].projname;
                        d.projdate=this.project[l].projdate;
                        d.projdescription=this.project[l].projdescription;
                        d.projectjoiner=this.project[l].projectjoiner;
                        d.projectflag=this.project[l].projectflag;
                        this.current_proj.push(d)
                    }
                    }
                }else{
                    var paperremain;
                    paperremain=this.projtotal-10*(val-1);
                    if(this.paperremain<=10){

                    var m=10*(val-1);
                    for(var n=m;n<(m+paperremain);n++){
                        var e={
                        projid:'',
                        projuserid:'',
                        projname:'',
                        projdate:'',
                        projdescription:'',
                        projectjoiner:'',
                        projectflag:''
                    };
                        e.projid=this.project[n].projid;
                        e.projuserid=this.project[n].projuserid;
                        e.projname=this.project[n].projname;
                        e.projdate=this.project[n].projdate;
                        e.projdescription=this.project[n].projdescription;
                        e.projectjoiner=this.project[n].projectjoiner;
                        e.projectflag=this.project[n].projectflag;
                        this.current_proj.push(e)
                    }
                    }else{
                    var j1=10*(val-1);
                    for(var i1=j1;i1<j1+10;i1++){
                        var a1={
                        projid:'',
                        projuserid:'',
                        projname:'',
                        projdate:'',
                        projdescription:'',
                        projectjoiner:'',
                        projectflag:''
                    };
                        a1.projid=this.project[i1].projid;
                        a1.projuserid=this.project[i1].projuserid;
                        a1.projname=this.project[i1].projname;
                        a1.projdate=this.project[i1].projdate;
                        a1.projdescription=this.project[i1].projdescription;
                        a1.projectjoiner=this.project[i1].projectjoiner;
                        a1.projectflag=this.project[i1].projectflag;
                        this.current_proj.push(a1)
                    }
                    }
                }


            },
            change_current_smg(val){
            this.current_smg=[];
            if(val==1){
                if(this.smgtotal<=10){

                for(var i2=0;i2<this.smgtotal;i2++){
                    var a2={
                    smgid:'',
                    smgowners:'',
                    smgtype:'',
                    smgrelationid:'',
                    smglike:'',
                    smglikenum:'',
                    smgdescription:'',
                    smgdate:'',
                    smgflag:''
                };
                    a2.smgid=this.statemsg[i2].smgid;
                    a2.smgowners=this.statemsg[i2].smgowners;
                    a2.smgtype=this.statemsg[i2].smgtype;
                    a2.smgrelationid=this.statemsg[i2].smgrelationid;
                    a2.smglike=this.statemsg[i2].smglike;
                    a2.smglikenum=this.statemsg[i2].smglikenum;
                    a2.smgdescription=this.statemsg[i2].smgdescription;
                    a2.smgdate=this.statemsg[i2].smgdate;
                    a2.smgflag=this.statemsg[i2].smgflag;
                    this.current_smg.push(a2)
                }
                }else{

                for(var i3=0;i3<10;i3++){
                    var a3={
                    smgid:'',
                    smgowners:'',
                    smgtype:'',
                    smgrelationid:'',
                    smglike:'',
                    smglikenum:'',
                    smgdescription:'',
                    smgdate:'',
                    smgflag:''
                };
                    a3.smgid=this.statemsg[i3].smgid;
                    a3.smgowners=this.statemsg[i3].smgowners;
                    a3.smgtype=this.statemsg[i3].smgtype;
                    a3.smgrelationid=this.statemsg[i3].smgrelationid;
                    a3.smglike=this.statemsg[i3].smglike;
                    a3.smglikenum=this.statemsg[i3].smglikenum;
                    a3.smgdescription=this.statemsg[i3].smgdescription;
                    a3.smgdate=this.statemsg[i3].smgdate;
                    a3.smgflag=this.statemsg[i3].smgflag;
                    this.current_smg.push(a3)
                }
                }
            }else{
                var paperremain;
                paperremain=this.smgtotal-10*(val-1);
                if(this.paperremain<=10){

                var j4=10*(val-1);
                for(var i4=j4;i4<(j4+paperremain);i4++){
                    var a4={
                    smgid:'',
                    smgowners:'',
                    smgtype:'',
                    smgrelationid:'',
                    smglike:'',
                    smglikenum:'',
                    smgdescription:'',
                    smgdate:'',
                    smgflag:''
                };
                    a4.smgid=this.statemsg[i4].smgid;
                    a4.smgowners=this.statemsg[i4].smgowners;
                    a4.smgtype=this.statemsg[i4].smgtype;
                    a4.smgrelationid=this.statemsg[i4].smgrelationid;
                    a4.smglike=this.statemsg[i4].smglike;
                    a4.smglikenum=this.statemsg[i4].smglikenum;
                    a4.smgdescription=this.statemsg[i4].smgdescription;
                    a4.smgdate=this.statemsg[i4].smgdate;
                    a4.smgflag=this.statemsg[i4].smgflag;
                    this.current_smg.push(a4)
                }
                }else{
                var j5=10*(val-1);
                for(var i5=j5;i5<j5+10;i5++){
                    var a5={
                    smgid:'',
                    smgowners:'',
                    smgtype:'',
                    smgrelationid:'',
                    smglike:'',
                    smglikenum:'',
                    smgdescription:'',
                    smgdate:'',
                    smgflag:''
                };
                    a5.smgid=this.statemsg[i5].smgid;
                    a5.smgowners=this.statemsg[i5].smgowners;
                    a5.smgtype=this.statemsg[i5].smgtype;
                    a5.smgrelationid=this.statemsg[i5].smgrelationid;
                    a5.smglike=this.statemsg[i5].smglike;
                    a5.smglikenum=this.statemsg[i5].smglikenum;
                    a5.smgdescription=this.statemsg[i5].smgdescription;
                    a5.smgdate=this.statemsg[i5].smgdate;
                    a5.smgflag=this.statemsg[i5].smgflag;
                    this.current_smg.push(a5)
                }
                }
            }


            },
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
                this.type=key;
            },

            //查看详情
            seeDetail(index){
                this.dialogVisible=true;
                this.d_smgowners=this.current_smg[index].smgowners;
                this.d_smglike=this.current_smg[index].smglike;
                this.d_smglikenum=this.current_smg[index].smglikenum;
                this.d_smgdescription=this.current_smg[index].smgdescription;
                this.d_smgdate=this.current_smg[index].smgdate;

            },
            //点赞动态
            likesmg(index){
                var sid=parseInt(this.current_smg[index].smgid);
                var uid=parseInt(this.userid);
                this.$http
                .post("/api/likeSmg", {
                smgid:sid,
                userid:uid,
                })
                .then((res) => {
                    this.$message.success(res.data.message);
                    this.current_smg[index].smglikenum++;
                    this.current_smg[index].smgflag=1;
                    console.log(res);
                })
                .catch((err) => {
                this.$message.error("网络错误,请稍后再试!");
                });

            },
            joinProject(index){
                var pid=this.current_proj[index].projid;
                var pid=parseInt(pid);
                //alert(pid);
                var uid=parseInt(this.userid);
                //alert(uid);
                this.$http
                .post("/api/joinProject", {
                userid:uid,
                projid:pid,
                })
                .then((res) => {
                    this.$message.success(res.data.message);
                    this.current_proj[index].projectflag=1;
                    console.log(res);
                })
                .catch((err) => {
                this.$message.error("网络错误,请稍后再试!");
                });
            }
            //加入项目


        },
    };
</script>

<style scoped>
.button1 {
  color: #42b983;
  border-color: #42b983;
  border-radius: 1.5vw;
  height: 2.8vw;
  border-width: 1px;
}

        .whole {
        width: 65%;
        height: 100%;
  margin: 10vh auto;
        background-color: #ffffff;
        font-family: "Microsoft YaHei";
        min-width: 500px;
        }
        .search_list {
        margin-top: 40px;
        min-height: 50px;
        }
        .list_card {
        padding: 10px 35px;
        border-top: 1px dashed #ccc;
        border-right: 1px solid #ccc;
        border-left: 1px solid #ccc;
        }
        .title {
        padding: 7px 0;
        font-size: 20px;
        font-family: "Microsoft YaHei";
        font-weight: bold;
        color: #215db1;
        user-select: none;
        }
        .title:hover {
        color: #ff6c00;
        cursor: pointer;
        }
        .date {
        float: right;
        margin-right: 100px;
  vertical-align: middle;
        font-size: 16px;
        user-select: none;
        color: #666666;
        }
        .abstract {
        font-size: 16px;
        color: #666666;
        user-select: none;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        }
        .button {
        font-size: 18px;
        color: #417dc9;
        display: inline-block;
        padding: 4px 10px;
        border-radius: 5px;
        border: 1px solid #417dc9;
        user-select: none;
        }
        .button:hover {
        background-color: #417dc9;
        color: white;
        cursor: pointer;
        }
        .card_foot {
        float: right;
  font-size: 15px;
  font-weight: 500;
  color: #0b9ff3;
        }

</style>
