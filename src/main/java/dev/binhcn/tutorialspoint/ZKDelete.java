package dev.binhcn.tutorialspoint;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.KeeperException;

public class ZKDelete {
   private static ZooKeeper zk;
   private static ZooKeeperConnection conn;

   // Method to check existence of znode and its status, if znode is available.
   public static void delete(String[] paths) throws KeeperException,InterruptedException {
      for (int i=0; i<paths.length; i++) {
         String path = "/payment-engine/asset-exchange/" + paths[i];
         deletePath(path + "/processing");
         deletePath(path);
      }
   }

   public static void deletePath(String path) {
      try {
         zk.delete(path, zk.exists(path, true).getVersion());
      } catch(Exception ex) {
         System.out.println(ex.getMessage());
      }
   }

   public static void main(String[] args) throws InterruptedException,KeeperException {
      String[] paths = new String[] {}; //Assign path to the znode

      try {
         conn = new ZooKeeperConnection();
         zk = conn.connect("10.60.45.50:2182,10.60.45.51:2182,10.60.45.52:2182");
         delete(paths); //delete the node with the specified path
      } catch(Exception e) {
         System.out.println(e.getMessage()); // catches error messages
      }
   }
}