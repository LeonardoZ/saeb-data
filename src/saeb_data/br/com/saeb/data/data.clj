(ns saeb-data.br.com.saeb.data.data)


(defn lazy-file-lines [file]
  "Read file content line-by-line (lazy)."
  (letfn [(helper [rdr]
            (lazy-seq
              (if-let [line (.readLine rdr)]
                (cons line (helper rdr))
                (do (.close rdr) nil))))]
    (helper (clojure.java.io/reader file :encoding "UTF-8"))))


(defn iter-for-cities
  "iterate line searching for cities. it receives a base-repo with already saved cities"
  [base-repo lines]
  (loop [repo base-repo
         xs lines]
    (if (empty? xs)
      repo
      (let [head (first xs)
            tail (rest xs)
            [year state city-name code & rest] (clojure.string/split head #";")
            city-code (keyword code)
            new-repo (if (contains? repo city-code)
                       (update repo city-code conj city-name)
                       (assoc repo city-code #{city-name}))]
        (recur new-repo tail)))))

