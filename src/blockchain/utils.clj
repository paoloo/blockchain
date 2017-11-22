(ns blockchain.utils
    (:import [java.security.MessageDigest]
             [java.net.URL]
             [java.util.UUID]))

(defn sha256
    [data]
    (.digest (java.security.MessageDigest/getInstance "SHA-256") (bytes (byte-array (map byte data)))))

(defn hash-str
    [data-bytes]
    (->> data-bytes
         (map #(.substring
                (Integer/toString
                 (+ (bit-and % 0xff) 0x100) 16) 1))
         (apply str)))

(defn sha256hash
    [data]
    (hash-str (sha256 data)))

(defn uuidv4
  []
  (str (java.util.UUID/randomUUID)))

(defn URLnetLoc
  [-url]
  (let [u (java.net.URL. -url)] (str (.getHost u) ":" (.getPort u))))
