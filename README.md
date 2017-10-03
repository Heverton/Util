# Util
==========

[![](https://jitpack.io/v/Heverton/util.svg)](https://jitpack.io/#Heverton/util)

Framework de funções uteis para Java.

+ Como utilizar a biblioteca:

          <distributionManagement>
           <repository>
             <id> internal.repo </id>
               <name>Temporary Staging Repository </name>
               <url>file://${project.build.directory}/mvn-repo</url>
           </repository>
          </distributionManagement>

          <dependencies>
             <dependency>
               <groupId>br.com.util</groupId>
                 <artifactId>com-util</artifactId>
                 <version>1.2</version>
             </dependency>
          </dependencies>
