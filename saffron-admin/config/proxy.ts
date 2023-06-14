/**
 * @name 代理的配置
 * @see 在生产环境 代理是无法生效的，所以这里没有生产环境的配置
 * -------------------------------
 * The agent cannot take effect in the production environment
 * so there is no configuration of the production environment
 * For details, please see
 * https://pro.ant.design/docs/deploy
 *
 * @doc https://umijs.org/docs/guides/proxy
 */
export default {
  // 如果需要自定义本地开发服务器  请取消注释按需调整
  dev: {
    // localhost:8000/api/** -> https://preview.pro.ant.design/api/**
    "/saffron-server":{
      target: "http://127.0.0.1:20020",
      changeOrigin: true,
      pathRewrite: { '^/saffron-server': '/saffron-server' },
    },
    "/saffron_files":{
      target: "http://127.0.0.1:20020",
      changeOrigin: true,
      pathRewrite: { '^/saffron_files': '/saffron-server/file/preview/saffron_files' },
    }
  },
};
