<template>
  <div id="loading-spinner" class="loading">
    <div class="spinner-wrap">
      <fulfilling-bouncing-circle-spinner :animation-duration="1500" :size="60" color="#e4f5ff88"/>
    </div>
  </div>

</template>

<script>
import {FulfillingBouncingCircleSpinner} from 'epic-spinners';

export default {

  components: {
    FulfillingBouncingCircleSpinner,
  },

  data() {
    return {

    }
  },

  computed: {

  },
  created() {
    /* axios Interceptors 설정 */
    let me = this;
    axios.interceptors.request.use(function (request) {
      me.setLoadingSpinner(true)
      return request;
    }, function (error) {
      me.setLoadingSpinner(false);
      return Promise.reject(error);
    });

    axios.interceptors.response.use(function (response) {
      me.setLoadingSpinner(false);
      return response;
    }, function (error) {
      me.setLoadingSpinner(false);
      return Promise.reject(error);
    });
  },

  methods: {
    setLoadingSpinner(on) {
      if (on === true) {
        document.getElementById("loading-spinner").classList.add("on");
      } else {
        document.getElementById("loading-spinner").classList.remove("on");
      }
    }
  }

}
</script>
