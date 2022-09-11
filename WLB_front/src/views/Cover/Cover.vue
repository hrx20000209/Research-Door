<template>
  <div>
    <div
        style="position: fixed; background: white; width: 100vw; z-index: 1000; top: 0; height: 4vw; box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;">
      <router-link to="/"
                   style="text-decoration: none; color: #42b983; font-family: 'Rockwell',serif; float: left; font-weight: 700; margin: 1vw 10vw; height: 2vw; line-height: 2vw; font-size: 1.5vw">
        ResearchDoor
      </router-link>
      <router-link to="/login"
                   style="text-decoration: none;color: #42b983; font-family: 'system-ui',serif; float: right; margin: 1vw 10vw 1vw 0; height: 2vw; line-height: 2vw; font-size: 1vw">
        Join for Free
      </router-link>
      <router-link to="/login"
                   style="text-decoration: none;color: darkgray; font-family: 'system-ui',serif; float: right; margin: 1vw 3vw; height: 2vw; line-height: 2vw; font-size: 1vw">
        Login
      </router-link>
    </div>
    <div
        style="color: black;; z-index: 0; position: relative; top: 25vh; left: 10vw;width: 25vw; font-size: 60px; font-weight: 1000;"
        data-wow-duration="1.5s" data-wow-delay="0.05s">
      Access to Knowledge <br>
      By Joining <br>
      ResearchDoor
    </div>
    <div
        style="z-index: 0; position: relative; top: -20vh; left: 50vw;width: 25vw; "
    >
      <img :src="cover" style="object-fit: contain; margin-top: 0; width: 35vw;" alt="">
    </div>
    <div style="position: absolute; top: 40vh; left: 15vw;width: 40vw" class="wow bounceInUp" data-wow-duration="2.5s"
         data-wow-delay="0.25s">
      <div class="search">
        <el-input style="width: 60vw; " placeholder="search for publication, author or questions" v-model="searchinput"
                  class="input-with-select">
          <el-select style="width:120px" v-model="selectType" slot="prepend" placeholder="select">
            <el-option label="publication" value="1"></el-option>
            <el-option label="author" value="2"></el-option>
            <el-option label="questions" value="3"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search"></el-button>
        </el-input>
      </div>
    </div>
    <div style="position: absolute; top: 65vh; left: 35vw;">
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" data-v-152cbb9b="">
        <path fill="currentColor"
              d="m795.904 750.72 124.992 124.928a32 32 0 0 1-45.248 45.248L750.656 795.904a416 416 0 1 1 45.248-45.248zM480 832a352 352 0 1 0 0-704 352 352 0 0 0 0 704z"></path>
      </svg>
    </div>

    <div style="margin-top: 120vh;height: 100vh; display: none;">
      <div class="wow bounceInUp" data-wow-duration="2.2s" data-wow-delay="0.25s">
        <h1 style="font-size: 45px;font-weight:1000;text-align: center;"> Start Your Exploration</h1>
        <div class="wow bounceInRight" data-wow-duration="2s" data-wow-delay="0.35s"
             style="float: right; width: 45vw;margin-top: 32vh; margin-right: 11vw; height: 25vw">
          <div style="margin-bottom: 3vh;font-size: 45px; font-weight: 800">
            Discover research
          </div>
          <div style="margin-bottom: 3vh;font-size: 25px; font-weight: 200;">
            Access over 135 million publication pages and stay up to date with what's happening in your field.
          </div>
        </div>
      </div>
    </div>

    <div style="margin-top: 10vh;height: 100vh">
      <div class="wow bounceInUp" data-wow-duration="2.5s" data-wow-delay="0.15s">
        <h1 style="font-size: 45px;font-weight:1000;text-align: center;"> Start Your Exploration</h1>
      </div>
      <img :src="SearchImg" alt=""
           style="float: left; width: 25vw; margin-top: 12vh; margin-left: 11vw; margin-right: 8vw"
           class="wow bounceInLeft">
      <div class="wow bounceInRight" data-wow-duration="2s" data-wow-delay="0.35s"
           style="float: right; width: 35vw;margin-top: 22vh; margin-right: 11vw; height: 25vw">
        <div style="margin-bottom: 3vh;font-size: 45px; font-weight: 800">
          Discover research
        </div>
        <div style="margin-bottom: 3vh;font-size: 25px; font-weight: 200;">
          Access over 135 million publication pages and stay up to date with what's happening in your field.
        </div>
        <el-input v-model="searchContent"  style="width: 25vw;"
                  placeholder="search for publication, author or questions">
          <el-button @click="search()" slot="append" icon="el-icon-search"></el-button>

        </el-input>
      </div>
    </div>
    <div style="margin-top: 10vh; height: 100vh">
      <div class="wow bounceInUp" >
        <h1 style="font-size: 50px;font-weight:1000;text-align: center;">scientific &nbsp;&nbsp;&nbsp; community</h1>
      </div>
      <div style="display: inline-block; width: 32vw; margin-top: 15vh; margin-left: 15vw" class="wow bounceInLeft"
           data-wow-duration="2s" data-wow-delay="0.35s">
        <div style="margin-bottom: 3vh;font-size: 45px; font-weight: 800">
          Connect with your scientific community
        </div>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px;">Mechanics
        </el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">Computer
          Science
        </el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">EE</el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">Integrated
          circuit
        </el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">AI</el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">Software
          Engineering
        </el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">Aircraft Design
        </el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">Astrophysics
        </el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">Geophysical
        </el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">inorganic
          chemistry
        </el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">molecular
          biology
        </el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">industrial
          design
        </el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">Condensed matter
          and materials
        </el-button>
        <el-button type="button" class="btn btn-outline-primary" style="margin: 0.5vw; font-size:15px">waiting ahead......
        </el-button>
      </div>
      <img :src="hotpaperImg" class="wow bounceInRight" alt=""
           style="float: right; width: 25vw;margin-top: 12vh; margin-right: 11vw;">
    </div>
    <div style="margin-top: 10vh; height: 100vh">
      <div class="wow bounceInUp" data-wow-duration="2.5s" data-wow-delay="0.15s">
        <h1 style="font-size: 45px;font-weight:1000;text-align: center;">Build Your Roadmap &nbsp;&nbsp;&nbsp; </h1>
      </div>
      <img :src="clickImg" class="wow bounceInLeft" data-wow-duration="2s" data-wow-delay="0.35s" alt=""
           style="float: left; width: 25vw;margin-top: 12vh; margin-left: 11vw;">
      <div class="wow bounceInRight" data-wow-duration="2s" data-wow-delay="0.35s"
           style="float: right; width: 45vw;margin-top: 32vh; margin-right: 11vw; height: 25vw">
        <div style="margin-bottom: 3vh;font-size: 45px; font-weight: 800">
          Measure your impact
        </div>
        <div style="margin-bottom: 3vh;font-size: 25px; font-weight: 200;">
          Get in-depth stats on who's been reading your work and keep track of your citations.
        </div>
      </div>

    </div>
    <div style="margin-top: 10vh; height: 100vh">
      <div class="wow bounceInUp" data-wow-duration="2.5s" data-wow-delay="0.15s">
        <h1 style="font-size: 45px;font-weight:1000;text-align: center;">Expand Your Social Range
          &nbsp;&nbsp;&nbsp; </h1>
      </div>
      <img :src="social" class="wow bounceInLeft" data-wow-duration="2s" data-wow-delay="0.35s" alt=""
           style="float: right; width: 25vw;margin-top: 12vh; margin-right: 11vw;">
      <div class="wow bounceInRight" data-wow-duration="2s" data-wow-delay="0.35s"
           style="float: right; width: 45vw;margin-top: 12vh; margin-left: 11vw; height: 45vw">
        <div id="socialChart" style="width: 25vw; height: 25vw;"></div>
      </div>
    </div>
    <div style="margin-top: 10vh; height: 100vh">
      <div class="wow bounceInUp" data-wow-duration="2.5s" data-wow-delay="0.15s">
        <h1 style="font-size: 45px;font-weight:1000;text-align: center;"> Advance your research <br> and join a
          community of
          <br> 9 scientists
          &nbsp;&nbsp;&nbsp; </h1>
      </div>
      <img :src="social" class="wow bounceInLeft" data-wow-duration="2s" data-wow-delay="0.35s" alt=""
           style="float: right; width: 25vw;margin-top: 12vh; margin-right: 11vw;">
      <el-button @click="$router.push('/login')" style="margin-top: 26vh;width: 30vw; height: 10vw; color: white; font-size: 3vw; font-weight: 1000; background: #42b983;">Join for Free!</el-button>
    </div>
        <el-backtop/>
  </div>
</template>
<script>

import {WOW} from 'wowjs'
import * as echarts from 'echarts';
import 'axios'
import $ from 'jquery'

export default {
  name: "Cover",
  components: {},

  data() {
    return {
      cover: require('../../assets/images/Cover/cover4.png'),
      inputSearch: '',
      restaurants: [],
      searchContent: '',
      selectType: '',
      state: '',
      timeout: null,
      searchStyle: 'font-size:23px; height: 10.5vh;border-radius: 2.5vw; width: 70vw;',
      activeName: 'first',
      SearchImg: require('../../assets/images/Cover/research.png'),
      SearchImg1: require('../../assets/images/Cover/research1.png'),
      social: require('../../assets/images/Cover/social.png'),
      clickImg: require('../../assets/images/Cover/clickimg.png'),
      hotpaper: require('../../assets/images/Cover/hotpaper.jpg'),
      hotpaperImg: require('../../assets/images/Cover/hotpaper1.png'),

    }
  },
  methods: {

    handleSelect(item) {
      console.log(item);
    },
    search() {
      if (this.searchContent == '')
        this.$message.error("Please input Keyword")
      else {
        this.$router.push({
          path: '/search/researcher',
          query: {
            keyword: this.searchContent
          }
        })
      }
    }

  },
  mounted() {
    console.log('start!')
    // 基于准备好的dom，初始化echarts实例
    var ROOT_PATH = 'https://echarts.apache.org/examples';

    var chartDom = document.getElementById('socialChart');
    var myChart = echarts.init(chartDom);
    var option;

    myChart.showLoading();
    const data = [
      {
        fixed: true,
        x: myChart.getWidth() / 2,
        y: myChart.getHeight() / 2,
        symbolSize: 20,
        id: '-1'
      }
    ];
    const edges = [];
    option = {
      series: [
        {
          type: 'graph',
          layout: 'force',
          animation: false,
          data: data,
          force: {
            // initLayout: 'circular'
            // gravity: 0
            repulsion: 100,
            edgeLength: 5
          },
          edges: edges
        }
      ]
    };
    setInterval(function () {
      data.push({
        id: data.length + ''
      });
      var source = Math.round((data.length - 1) * Math.random());
      var target = Math.round((data.length - 1) * Math.random());
      if (source !== target) {
        edges.push({
          source: source,
          target: target
        });
      }
      myChart.setOption({
        series: [
          {
            roam: true,
            data: data,
            edges: edges
          }
        ]
      });
      // console.log('nodes: ' + data.length);
      // console.log('links: ' + data.length);
    }, 500);
    option && myChart.setOption(option);
    myChart.hideLoading();

    this.$nextTick(() => {
      new WOW().init(
          {live: false}
      )
      console.log('wooow')
    })
  }

}
</script>

<style scoped>
html {
  padding: 0;
  margin: 0;
}

body {
  padding: 0;
  margin: 0;

}

.search {
  position: fixed;
  margin: 50vh 18vw;
  width: 80vw;
  top: 52vh;
  font-size: 8vh;
}

.btn-outline-primary {
  color: #42b983;
  border-color: #42b983;
  border-radius: 1.5vw;
  height: 3vw;
  border-width: 1px;
}

button {
  display: inline-block;
}
</style>
<style>
.popper {
  margin-left: 15vw;
}

</style>
