module.exports = function(grunt) {

  grunt.initConfig({

    pkg: grunt.file.readJSON('package.json'),

    bower_concat: {
      all: {
        dest: 'src/main/webapp/resources/js/lib.js',
        cssDest: 'src/main/webapp/resources/style/lib.css',
        bowerOptions: {
          relative: false
        }
      }
    },

    concat: {
      js: {
        src: [ 'src/main/ui/app/**/*.js', 'src/main/ui/*.js' ],
        dest: 'src/main/webapp/resources/js/app.js'
      }
    },

    copy: {
      tomcat: {
        src: ['src/main/ui/web.xml'],
        dest: 'src/main/webapp/resources/web.xml'
      },
      resources: {
        expand: true,
        cwd: 'src/main/resources/',
        src: ['**/**/*'],
        dest: 'src/main/webapp/resources/resources/'
      },
      config: {
        expand: true,
        cwd: 'src/main/ui/config/',
        src: ['**/**/*'],
        dest: 'src/main/webapp/resources/config/'
      },
      templates: {
        expand: true,
        cwd: 'src/main/ui/',
        src: ['login.html', 'dashboard.html', 'templates/*.html'],
        dest: 'src/main/webapp/resources/'
      },
    },

    uglify: {
      js: {
        src: '<concat.js.dest>',
        dest: 'src/main/webapp/resources/js/app.min.js'
      }
    },

    jshint: {
      options: {
        curly:    false,
        eqeqeq:   true,
        immed:    true,
        latedef:  false,
        newcap:   true,
        noarg:    true,
        sub:      true,
        undef:    false,
        unused:   true,
        boss:     true,
        eqnull:   true,
        browser:  true,
        globals: {
          jQuery: true
        }
      },
      all: ['Gruntfile.js', 'src/main/ui/app/**/*.js']
    },

    qunit: {
      files: ['test/**/*.html']
    },

    watch: {
      js: {
        files: '<jshint.all>',
        tasks: ['concat:js']
      },
      templates: {
        files: ['src/main/ui/login.html',
                'src/main/ui/dashboard.html',
                'src/main/ui/templates/**/*.html'],
        tasks: [ 'copy:templates' ]
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-qunit');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-bower-concat');

  grunt.registerTask('default', ['bower_concat', 'copy', 'concat', 'uglify']);
  grunt.registerTask('dev', ['bower_concat', 'concat', 'copy' ]);
};
