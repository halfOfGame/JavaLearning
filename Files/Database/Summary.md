[TOC]



## 常见问题

- 排名问题
  1. 普通排名
  
     ```sql
     -- 定义一个变量curRank表示当前的排名
     -- 思路：遍历所有记录，因为没有重复元素，curRank每次自增1即可
     
     SELECT 
     	pid, name, age, @curRank := @curRank + 1 AS rank
     FROM players p, (
     SELECT @curRank := 0
     ) q
     ORDER BY age
     ```
  
  2. 普通并列排名
  
     ```sql
     -- 定义两个变量prevRank，curRank
     -- prevRank：表示上一个age
     -- curRank：表示当前排名
     -- 思路：遍历所有记录，如果当前的age等于上一个age，则排名不需要变化；如果不等于，则当前排名为curRank加1，并且curRank需要自增1
     
     SELECT
     	pid, name, age, 
     	CASE 
     	WHEN @prevRank = age
     		THEN @curRank 
     	WHEN @prevRank := age
     		THEN @curRank := @curRank + 1
     	END AS rank
     FROM players p,
     ( SELECT @curRank :=0, @prevRank := NULL ) r
     ORDER BY age
     ```
  
  3. 高级并列排名
  
     ```sql
     -- 定义三个变量prevRank，curRank，inRank
     -- prevRank：表示上一个age
     -- curRank：表示当前排名
     -- incRank：表示当前已经排名的个数
     -- 思路：遍历所有记录，如果当前的age等于上一个age，则排名不需要变化，incRank需要自增1；如果不等于则
     
     SELECT
     	pid, name, age, rank
     FROM
     	(SELECT 
          	pid, name, age,
     		@curRank := IF( @prevRank = age, @curRank, @incRank ) AS rank, 
     		@incRank := @incRank + 1,
     		@prevRank := age
     		FROM players p, (
  		SELECT @curRank :=0, @prevRank := NULL, @incRank := 1 ) r 
     	ORDER BY age) s
     ```
     
     
  
- 连续相同数字或者连续递增加一数字

  ```sql
  Logs 表:
  +----+-----+
  | Id | Num |
  +----+-----+
  | 1  |  1  |
  | 2  |  1  |
  | 3  |  1  |
  | 4  |  2  |
  | 5  |  1  |
  | 6  |  2  |
  | 7  |  2  |
  +----+-----+
  
  Result 表
  +-----------------+
  | ConsecutiveNums |
  +-----------------+
  | 1               |
  +-----------------+
  
  
  -- 定义两个变量pre，count
  -- pre：表示上一个Num
  -- count：表示已经连续的数量
  -- 思路：遍历所有记录，如果当前Num和pre相等，则count自增1；否则count重置为1。最后筛选出所有大于三的记录，并且用DISTINCT修饰
  
  SELECT
      DISTINCT Num ConsecutiveNums 
  FROM ( 
      SELECT
          Num, if(@pre = Num, @count := @count + 1, @count := 1) nums, @pre := Num
      FROM
          Logs l, (SELECT @pre := null, @count := 1) pc
      ) n
  WHERE
      nums >= 3;
  ```

  

- 逐步求和，前1个数的和，前2个数的和

  ```sql
  -- 查询玩家到目前为止玩了几个游戏
  
  Activity 表:
  +-----------+-----------+------------+--------------+
  | player_id | device_id | event_date | games_played |
  +-----------+-----------+------------+--------------+
  | 1         | 2         | 2016-03-01 | 5            |
  | 1         | 2         | 2016-05-02 | 6            |
  | 1         | 3         | 2017-06-25 | 1            |
  | 3         | 1         | 2016-03-02 | 0            |
  | 3         | 4         | 2018-07-03 | 5            |
  +-----------+-----------+------------+--------------+
  
  Result 表:
  +-----------+------------+---------------------+
  | player_id | event_date | games_played_so_far |
  +-----------+------------+---------------------+
  | 1         | 2016-03-01 | 5                   |
  | 1         | 2016-05-02 | 11                  |
  | 1         | 2017-06-25 | 12                  |
  | 3         | 2016-03-02 | 0                   |
  | 3         | 2018-07-03 | 5                   |
  +-----------+------------+---------------------+
  
  
  -- 思路：自联结并限制条件，t1表的日期大于等于t2表的
  
  SELECT
  	a1.player_id, a1.event_date, sum(a2.games_played) games_played_so_far
  FROM
  	Activity a1 JOIN Activity a2
  ON a1.player_id=a2.player_id AND a1.event_date >= a2.event_date
  GROUP BY a1.player_id, a1.event_date
  ```
  
- 删除重复的记录，保留Id最小的那个

  ```sql
  Person 表:
  +----+------------------+
  | Id | Email            |
  +----+------------------+
  | 1  | john@example.com |
  | 2  | bob@example.com  |
  | 3  | john@example.com |
  +----+------------------+
  Id 是这个表的主键。
  
  Result 表:
  +----+------------------+
  | Id | Email            |
  +----+------------------+
  | 1  | john@example.com |
  | 2  | bob@example.com  |
  +----+------------------+
  
  
  -- 思路：通过内联结，选出那些Email相同并且Id较大的，删除即可
  
  DELETE
  	p1 
  FROM
  	Person p1 JOIN Person p2
  ON
  	p1.Email = p2.Email AND p1.Id > p2.Id
  ```

  

- 求第n高的记录

  1. 如果有多个只返回一个

     > `ORDER BY`然后再`LIMIT n, 1`输出

  2. 如果有多个就返回多个

     > 先用`ORDER BY`然后再`LIMIT n, 1`输出第n高的数，再用`WHERE`限制条件

- 筛选出所有当前的气温大于前一天的气温的Id

  ```sql
  weather 表：
  +---------+------------------+------------------+
  | Id(INT) | RecordDate(DATE) | Temperature(INT) |
  +---------+------------------+------------------+
  |       1 |       2015-01-01 |               10 |
  |       2 |       2015-01-02 |               25 |
  |       3 |       2015-01-03 |               20 |
  |       4 |       2015-01-04 |               30 |
  +---------+------------------+------------------+
  
  Result 表：
  +----+
  | Id |
  +----+
  |  2 |
  |  4 |
  +----+
  
  -- 直接用内联结
  
  SELECT
  	w1.id AS 'Id'
  FROM
  	weather w1 JOIN weather w2
  ON
  	DATEDIFF(w1.RecordDate, w2.RecordDate) = 1 AND w1.Temperature > w2.Temperature
  ```

- 查找最小日期对应的记录上的其他元素

  ```sql
  -- 描述每一个玩家首次登陆的设备名称
  Activity 表:
  +-----------+-----------+------------+--------------+
  | player_id | device_id | event_date | games_played |
  +-----------+-----------+------------+--------------+
  | 1         | 2         | 2016-03-01 | 5            |
  | 1         | 2         | 2016-05-02 | 6            |
  | 2         | 3         | 2017-06-25 | 1            |
  | 3         | 1         | 2016-03-02 | 0            |
  | 3         | 4         | 2018-07-03 | 5            |
  +-----------+-----------+------------+--------------+
  
  Result 表：
  +-----------+-----------+
  | player_id | device_id |
  +-----------+-----------+
  | 1         | 2         |
  | 2         | 3         |
  | 3         | 1         |
  +-----------+-----------+
  
  
  -- 思路：先查找出每个Id和Id对应的最小日期作为子表与原表联结，然后抽取原表的所需字段
  
  SELECT
  	a.player_id, a.device_id
  FROM Activity a JOIN(
      SELECT player_id, min(event_date) min_event_date
      FROM Activity
      GROUP BY player_id
  ) b
  ON a.player_id = b.player_id and a.event_date = b.min_event_date
  ```

- 查找首次登陆并且第二天也登录的玩家占所有玩家的比例

  ```sql
  Activity 表:
  +-----------+-----------+------------+--------------+
  | player_id | device_id | event_date | games_played |
  +-----------+-----------+------------+--------------+
  | 1         | 2         | 2016-03-01 | 5            |
  | 1         | 2         | 2016-03-02 | 6            |
  | 2         | 3         | 2017-06-25 | 1            |
  | 3         | 1         | 2016-03-02 | 0            |
  | 3         | 4         | 2018-07-03 | 5            |
  +-----------+-----------+------------+--------------+
  
  Result 表:
  +-----------+
  | fraction  |
  +-----------+
  | 0.33      |
  +-----------+
  
  -- 思路，先查找出所有玩家Id和首次登陆的时间作为t1左联结原表，联结条件为Id相等并且时间相差一天
  -- AVG(a.SELECTevent_date is not null)表示不为空的event_date占所有event_date的比例
  
  SELECT
  	ROUND(AVG(a.event_date is not null), 2) fraction
  from (
  	SELECT player_id, min(event_date) as login
      FROM activity
      GROUP BY player_id
      ) p  LEFT JOIN activity a
  ON
  	p.player_id = a.player_id AND DATEDIFF(a.event_date, p.login) = 1
  ```
  
- 两个字段需要满足不同的条件

  ```sql
  写一个查询语句，将 2016 年 (TIV_2016) 所有成功投资的金额加起来，保留 2 位小数。
  对于一个投保人，他在 2016 年成功投资的条件是：
  	他在 2015 年的投保额 (TIV_2015) 至少跟一个其他投保人在 2015 年的投保额相同。
  	他所在的城市必须与其他投保人都不同（也就是说维度和经度不能跟其他任何一个投保人完全相同）。
  	
  insurance 表
  | PID | TIV_2015 | TIV_2016 | LAT | LON |
  |-----|----------|----------|-----|-----|
  | 1   | 10       | 5        | 10  | 10  |
  | 2   | 20       | 20       | 20  | 20  |
  | 3   | 10       | 30       | 20  | 20  |
  | 4   | 10       | 40       | 40  | 40  |
  
  
  Result 表
  | TIV_2016 |
  |----------|
  | 45.00    |
  
  
  -- 思路：将两个条件分别用两个子查询实现，然后用WHERE去筛选
  SELECT
  	SUM(insurance.TIV_2016) AS TIV_2016
  FROM
  	insurance
  WHERE 
  	insurance.TIV_2015 IN(
        SELECT TIV_2015
        FROM insurance
        GROUP BY TIV_2015
        HAVING COUNT(*) > 1
      ) AND CONCAT(LAT, LON) IN (
        SELECT CONCAT(LAT, LON)
        FROM insurance
        GROUP BY LAT , LON
        HAVING COUNT(*) = 1
      )
  ```

  

- 谁有最多的好友

  ```sql
  表 request_accepted 存储了所有好友申请通过的数据记录，求出谁拥有最多的好友和他拥有的好友数目。
  
  request_accepted 表：
  | requester_id | accepter_id | accept_date|
  |--------------|-------------|------------|
  | 1            | 2           | 2016_06-03 |
  | 1            | 3           | 2016-06-08 |
  | 2            | 3           | 2016-06-08 |
  | 3            | 4           | 2016-06-09 |
  
  Result 表：
  | id | num |
  |----|-----|
  | 3  | 3   |
  
  -- 思路：通过UNION连接，将数据量翻倍，此时可以通过该表可以表示每个人的所有好友；如果有多个则需要将下列查询结果作为筛选条件。
  
  SELECT
  	rid id, COUNT(aid) num
  FROM(
  	SELECT R1.requester_id rid, R1.accepter_id aid
  	FROM request_accepted R1
  	UNION
  	SELECT R2.accepter_id rid,R2.requester_id aid
  	FROM request_accepted R2
  ) A
  GROUP BY rid
  ORDER BY num DESC
  LIMIT 1
  ```
  
- 连续空余座位

  ```mysql
  -- 查询连续座位，两个相邻空位
  
  cinema 表：
  | seat_id | free |
  |---------|------|
  | 1       | 1    |
  | 2       | 0    |
  | 3       | 1    |
  | 4       | 1    |
  | 5       | 1    |
  
  Result 表：
  | seat_id |
  |---------|
  | 3       |
  | 4       |
  | 5       |
  
  -- 思路：用差值的绝对值为1和两个都是空位为联结条件去联结，并且最终结果用DISTINCT去重
  
  SELECT
  	DISTINCT a.seat_id
  FROM
  	cinema a JOIN cinema b
  ON 
  	ABS(a.seat_id - b.seat_id) = 1 AND a.free = true AND b.free = true
  ORDER BY a.seat_id
  ```

- 树的结点

  ```sql
  -- tree Table表示id的父节点为p_id,将所有的节点分类
  
  tree 表：
  +----+------+
  | id | p_id |
  +----+------+
  | 1  | null |
  | 2  | 1    |
  | 3  | 1    |
  | 4  | 2    |
  | 5  | 2    |
  +----+------+
  
  Result 表：
  +----+------+
  | id | Type |
  +----+------+
  | 1  | Root |
  | 2  | Inner|
  | 3  | Leaf |
  | 4  | Leaf |
  | 5  | Leaf |
  +----+------+
  
  -- 思路：将tree表进行左自联结，如果t1.p_id为空，则该节点为'Root'；如果t2.id为空，则该节点为'Leaf'；否则该节点为'Inner'
  
  SELECT
      DISTINCT t1.id, 
          CASE 
          WHEN t1.p_id is null THEN 'Root'
          WHEN t2.id is null THEN 'Leaf'
          ELSE 'Inner' END Type
  FROM tree t1 LEFT JOIN tree t2
  ON
  	t1.id = t2.p_id
  ```

- 平面上的最近距离

  ```sql
  -- point_2d Table保存了所有的点，求出两个点之间的最近距离
  
  point_2d 表：
  | x  | y  |
  |----|----|
  | -1 | -1 |
  | 0  | 0  |
  | -1 | -2 |
  
  Result 表：
  | shortest |
  |----------|
  | 1.00     |
  ORAND
  
  
  -- 思路：将表进行内外联结，联结条件为 p1.x<p2.x OR (p1.x = p2.x AND p1.y < p2.y)
  -- 这样避免的重复的计算，因为点1到点2的距离和点2到点1的距离是相等的
  
  SELECT 
      ROUND(MIN(SQRT(POWER(p1.x-p2.x, 2) + POWER(p1.y-p2.y, 2))), 2) shortest
  FROM 
      point_2d p1 join point_2d p2
  ON p1.x < p2.x OR (p1.x = p2.x AND p1.y < p2.y)
  ```

- 改变相邻俩学生的座位(只要输出即可)，如果学生人数是奇数，则不需要改变最后一个同学的座位。

  ```sql
  Student 表：
  +---------+---------+
  |    id   | student |
  +---------+---------+
  |    1    | Abbot   |
  |    2    | Doris   |
  |    3    | Emerson |
  |    4    | Green   |
  |    5    | Jeames  |
  +---------+---------+
  
  Result 表：
  +---------+---------+
  |    id   | student |
  +---------+---------+
  |    1    | Doris   |
  |    2    | Abbot   |
  |    3    | Green   |
  |    4    | Emerson |
  |    5    | Jeames  |
  +---------+---------+
  
  -- 思路，交换学生的Id后再排序，如果为奇数且当前Id不等于最大的Id，输出Id+1；如果为奇数且当前Id等于最大的Id，输出Id；如果Id为偶数，则输出Id-1
  
  SELECT
      (CASE
          WHEN MOD(id, 2) != 0 AND counts != id
              THEN id + 1
          WHEN MOD(id, 2) != 0 AND counts = id
              THEN id
          ELSE id - 1
      END) id,
      student
  FROM
      seat,
      (SELECT COUNT(*) AS counts FROM seat) seat_counts
  ORDER BY id
  ```

- 更新数据

  ```sql
  -- 如果sex为'f'，则改为'm'；如果为'm'，则改为'f'
  
  UPDATE salary 
  SET sex = IF(sex='f','m','f')
  ```

- 查询从 `2019-06-30` 起最多 90 天内，每个日期该日期首次登录的用户数。

  ```sql
  Traffic 表：
  +---------+----------+---------------+
  | user_id | activity | activity_date |
  +---------+----------+---------------+
  | 1       | login    | 2019-05-01    |
  | 1       | homepage | 2019-05-01    |
  | 1       | logout   | 2019-05-01    |
  | 2       | login    | 2019-06-21    |
  | 2       | logout   | 2019-06-21    |
  | 3       | login    | 2019-01-01    |
  | 3       | jobs     | 2019-01-01    |
  | 3       | logout   | 2019-01-01    |
  | 4       | login    | 2019-06-21    |
  | 4       | groups   | 2019-06-21    |
  | 4       | logout   | 2019-06-21    |
  | 5       | login    | 2019-03-01    |
  | 5       | logout   | 2019-03-01    |
  | 5       | login    | 2019-06-21    |
  | 5       | logout   | 2019-06-21    |
  +---------+----------+---------------+
  
  Result 表：
  +------------+-------------+
  | login_date | user_count  |
  +------------+-------------+
  | 2019-05-01 | 1           |
  | 2019-06-21 | 2           |
  +------------+-------------+
  
  
  -- 思路：找到所有玩家第一次登陆游戏的时间，然后限制搜索条件
  
  SELECT
  	login_date, count(user_id) user_count
  FROM (
      SELECT user_id, min(activity_date) login_date
      FROM Traffic
      WHERE activity='login'
      GROUP BY user_id
  ) t
      WHERE datediff('2019-06-30',login_date) <= 90
      GROUP BY login_date;
  ```

- 查询每位学生获得的最高成绩和它所对应的科目,

  ```sql
  -- 查询每位学生获得的最高成绩和它所对应的科目，若科目成绩并列，取 course_id 最小的一门。查询结果需按 student_id 增序进行排序。
  
  Enrollments 表：
  +------------+-------------------+
  | student_id | course_id | grade |
  +------------+-----------+-------+
  | 2          | 2         | 95    |
  | 2          | 3         | 95    |
  | 1          | 1         | 90    |
  | 1          | 2         | 99    |
  | 3          | 1         | 80    |
  | 3          | 2         | 75    |
  | 3          | 3         | 82    |
  +------------+-----------+-------+
  
  Result 表：
  +------------+-------------------+
  | student_id | course_id | grade |
  +------------+-----------+-------+
  | 1          | 2         | 99    |
  | 2          | 2         | 95    |
  | 3          | 3         | 82    |
  +------------+-----------+-------+
  
  -- 思路：先找到每个同学Id和对应的最高分科目，然后再从满足条件的记录中筛选出符合条件的记录
  
  SELECT
  	student_id, min(course_id) as course_id, grade
  FROM
  	Enrollments
  WHERE (student_id,grade) IN (
      SELECT student_id, max(grade)
      FROM Enrollments
      GROUP BY student_id
  )
  GROUP BY student_id, grade
  ORDER BY student_id
  ```

- 查询活跃用户

  ```sql
  -- 如果一个业务的某个事件类型的发生次数大于此事件类型在所有业务中的平均发生次数，并且该业务至少有两个这样的事件类型，那么该业务就可被看做是活跃业务。
  
  Events 表:
  +-------------+------------+------------+
  | business_id | event_type | occurences |
  +-------------+------------+------------+
  | 1           | reviews    | 7          |
  | 3           | reviews    | 3          |
  | 1           | ads        | 11         |
  | 2           | ads        | 7          |
  | 3           | ads        | 6          |
  | 1           | page views | 3          |
  | 2           | page views | 12         |
  +-------------+------------+------------+
  
  Result 表：
  +-------------+
  | business_id |
  +-------------+
  | 1           |
  +-------------+ 
  
  -- 思路：求出每个event_type和对应的平均值，再与原表进行联结，然后筛选出满足条件的记录大于2的项目
  
  SELECT 
  	business_id
  FROM Events AS e
  JOIN (
      SELECT event_type, AVG(occurences) AS eventAvg
      FROM Events
      GROUP BY event_type
  ) AS e1 
  ON e.event_type = e1.event_type
  WHERE e.occurences > e1.eventAvg
  GROUP BY business_id
  HAVING COUNT(*) >= 2
  ```

- 被移除的帖子的每日平均占比

  ```sql
  -- 在被报告为垃圾广告的帖子中，被移除的帖子的每日平均占比
  
  Actions 表:
  +---------+---------+-------------+--------+--------+
  | user_id | post_id | action_date | action | extra  |
  +---------+---------+-------------+--------+--------+
  | 1       | 1       | 2019-07-01  | view   | null   |
  | 1       | 1       | 2019-07-01  | like   | null   |
  | 1       | 1       | 2019-07-01  | share  | null   |
  | 2       | 2       | 2019-07-04  | view   | null   |
  | 2       | 2       | 2019-07-04  | report | spam   |
  | 3       | 4       | 2019-07-04  | view   | null   |
  | 3       | 4       | 2019-07-04  | report | spam   |
  | 4       | 3       | 2019-07-02  | view   | null   |
  | 4       | 3       | 2019-07-02  | report | spam   |
  | 5       | 2       | 2019-07-03  | view   | null   |
  | 5       | 2       | 2019-07-03  | report | racism |
  | 5       | 5       | 2019-07-03  | view   | null   |
  | 5       | 5       | 2019-07-03  | report | racism |
  +---------+---------+-------------+--------+--------+
  
  Removals 表:
  +---------+-------------+
  | post_id | remove_date |
  +---------+-------------+
  | 2       | 2019-07-20  |
  | 3       | 2019-07-18  |
  +---------+-------------+
  
  Result 表:
  +-----------------------+
  | average_daily_percent |
  +-----------------------+
  | 75.00                 |
  +-----------------------+
  -- 2019-07-04 的垃圾广告移除率是 50%，因为有两张帖子被报告为垃圾广告，但只有一个得到移除。
  -- 2019-07-02 的垃圾广告移除率是 100%，因为有一张帖子被举报为垃圾广告并得到移除。
  -- 其余几天没有收到垃圾广告的举报，因此平均值为：(50 + 100) / 2 = 75%
  -- 注意，输出仅需要一个平均值即可，我们并不关注移除操作的日期。
  
  
  --思路：先找出每个日期和对应的垃圾广告移除率，再求平均移除率
  
  SELECT
  	ROUND(AVG(t.daily_percent) * 100, 2) average_daily_percent
  FROM(
  	SELECT 
      	count(distinct r.post_id) / count(distinct a.post_id) as daily_percent
  	from Actions a left join Removals r
  	on a.post_id = r.post_id
  	where a.extra = 'spam'
  	group by a.action_date
  ) as t
  ```

- 查找注册日期和某一年的订单数

  ```sql
  -- 以查询每个用户的注册日期和在 2019 年作为买家的订单总数。
  
  Users 表:
  +---------+------------+----------------+
  | user_id | join_date  | favorite_brand |
  +---------+------------+----------------+
  | 1       | 2018-01-01 | Lenovo         |
  | 2       | 2018-02-09 | Samsung        |
  | 3       | 2018-01-19 | LG             |
  | 4       | 2018-05-21 | HP             |
  +---------+------------+----------------+
  
  Orders 表:
  +----------+------------+---------+----------+-----------+
  | order_id | order_date | item_id | buyer_id | seller_id |
  +----------+------------+---------+----------+-----------+
  | 1        | 2019-08-01 | 4       | 1        | 2         |
  | 2        | 2018-08-02 | 2       | 1        | 3         |
  | 3        | 2019-08-03 | 3       | 2        | 3         |
  | 4        | 2018-08-04 | 1       | 4        | 2         |
  | 5        | 2018-08-04 | 1       | 3        | 4         |
  | 6        | 2019-08-05 | 2       | 2        | 4         |
  +----------+------------+---------+----------+-----------+
  
  Items 表:
  +---------+------------+
  | item_id | item_brand |
  +---------+------------+
  | 1       | Samsung    |
  | 2       | Lenovo     |
  | 3       | LG         |
  | 4       | HP         |
  +---------+------------+
  
  Result 表:
  +-----------+------------+----------------+
  | buyer_id  | join_date  | orders_in_2019 |
  +-----------+------------+----------------+
  | 1         | 2018-01-01 | 1              |
  | 2         | 2018-02-09 | 2              |
  | 3         | 2018-01-19 | 0              |
  | 4         | 2018-05-21 | 0              |
  +-----------+------------+----------------+
  
  -- 思路：将Users表和Orders表进行左联接，联结条件为user_id=buyer_id并且年份是2019，此条件可以将null值也包含进来
  
  SELECT
  	u.user_id as buyer_id, join_date, count(order_id) orders_in_2019
  FROM
  	Users u LEFT JOIN Orders o
  ON
  	u.user_id = o.buyer_id AND YEAR(o.order_date) = 2019
  GROUP BY
  	u.user_id
  ```

- 指定日期的产品价格

  ```sql
  -- 查找在 2019-08-16 时全部产品的价格，假设所有产品在修改前的价格都是 10。
  
  Products 表:
  +------------+-----------+-------------+
  | product_id | new_price | change_date |
  +------------+-----------+-------------+
  | 1          | 20        | 2019-08-14  |
  | 2          | 50        | 2019-08-14  |
  | 1          | 30        | 2019-08-15  |
  | 1          | 35        | 2019-08-16  |
  | 2          | 65        | 2019-08-17  |
  | 3          | 20        | 2019-08-18  |
  +------------+-----------+-------------+
  
  Result 表:
  +------------+-------+
  | product_id | price |
  +------------+-------+
  | 2          | 50    |
  | 1          | 35    |
  | 3          | 10    |
  +------------+-------+
  
  -- 思路：选出每件商品在2019-08-16日的上一次和下一次更新价格(为了找出那些在2019-08-16之前没改过价格的)的时间作为t1，原表联结t1表，联结条件为id和date都相等，此时从记录中选出更新日期小于2019-08-16的，价格为new_price，否则为10.
  
  SELECT
  	n.product_id, IF(n.last_date > '2019-08-16', 10, p.new_price) "price"
  FROM products p JOIN
  (
  	SELECT product_id, max(change_date) "last_date"
  	FROM products
  	WHERE change_date <= '2019-08-16'
  	GROUP BY product_id
     UNION
  	SELECT product_id, min(change_date) "last_date"
  	FROM products
  	GROUP BY product_id
  	HAVING min(change_date) > '2019-08-16'
  ) n
  ON
  	p.product_id = n.product_id AND p.change_date = n.last_date;
  ```

- 重新格式化数据表

  ```sql
  -- 重新格式化表，使得新的表中有一个部门 id 列和一些对应 每个月 的收入（revenue）列。
  
  Department 表：
  +------+---------+-------+
  | id   | revenue | month |
  +------+---------+-------+
  | 1    | 8000    | Jan   |
  | 2    | 9000    | Jan   |
  | 3    | 10000   | Feb   |
  | 1    | 7000    | Feb   |
  | 1    | 6000    | Mar   |
  +------+---------+-------+
  
  查询得到的结果表：
  +------+-------------+-------------+-------------+-----+-------------+
  | id   | Jan_Revenue | Feb_Revenue | Mar_Revenue | ... | Dec_Revenue |
  +------+-------------+-------------+-------------+-----+-------------+
  | 1    | 8000        | 7000        | 6000        | ... | null        |
  | 2    | 9000        | null        | null        | ... | null        |
  | 3    | null        | 10000       | null        | ... | null        |
  +------+-------------+-------------+-------------+-----+-------------+
  
  -- 注意，结果表有 13 列 (1个部门 id 列 + 12个月份的收入列)。
  
  -- 思路：按情况格式化每一列
  
  SELECT id,
  SUM(IF(`month`='Jan', revenue, NULL)) Jan_Revenue,
  SUM(IF(`month`='Feb', revenue, NULL)) Feb_Revenue,
  SUM(IF(`month`='Mar', revenue, NULL)) Mar_Revenue,
  SUM(IF(`month`='Apr', revenue, NULL)) Apr_Revenue,
  SUM(IF(`month`='May', revenue, NULL)) May_Revenue,
  SUM(IF(`month`='Jun', revenue, NULL)) Jun_Revenue,
  SUM(IF(`month`='Jul', revenue, NULL)) Jul_Revenue,
  SUM(IF(`month`='Aug', revenue, NULL)) Aug_Revenue,
  SUM(IF(`month`='Sep', revenue, NULL)) Sep_Revenue,
  SUM(IF(`month`='Oct', revenue, NULL)) Oct_Revenue,
  SUM(IF(`month`='Nov', revenue, NULL)) Nov_Revenue,
  SUM(IF(`month`='Dec', revenue, NULL)) Dec_Revenue
  FROM Department
  GROUP BY id;
  ```
  
- 每月交易

- 最后一个能进入电梯的人

  ```sql
  -- 电梯最大载重量为 1000，查找最后一个能进入电梯且不超过重量限制的 person_name 。题目确保队列中第一位的人可以进入电梯 。
  
  Queue 表：
  +-----------+-------------------+--------+------+
  | person_id | person_name       | weight | turn |
  +-----------+-------------------+--------+------+
  | 5         | George Washington | 250    | 1    |
  | 3         | John Adams        | 350    | 2    |
  | 6         | Thomas Jefferson  | 400    | 3    |
  | 2         | Will Johnliams    | 200    | 4    |
  | 4         | Thomas Jefferson  | 175    | 5    |
  | 1         | James Elephant    | 500    | 6    |
  +-----------+-------------------+--------+------+
  
  Result 表：
  +-------------------+
  | person_name       |
  +-------------------+
  | Thomas Jefferson  |
  +-------------------+
  
  
  -- 为了简化，Queue 表按 trun 列由小到大排序。
  -- 上例中 George Washington(id 5), John Adams(id 3) 和 Thomas Jefferson(id 6) 将可以进入电梯,因为他们的体重和为 250 + 350 + 400 = 1000。
  -- Thomas Jefferson(id 6) 是最后一个体重合适并进入电梯的人。
  
  
  -- 思路：设置变量找出id和id进入电梯时电梯的总重量，然后再从电梯重量小于1000的里面找出最大的那个人
  
  SELECT a.person_name
  FROM 
  (
  	SELECT person_name, @pre := @pre + weight AS weight
  	FROM Queue, (SELECT @pre := 0) tmp
  	ORDER BY turn
  ) a
  WHERE a.weight <= 1000
  ORDER BY a.weight DESC
  LIMIT 1
  
  ```

- 统计交易

  ```sql
  -- 查找每个月和每个国家/地区的已批准交易的数量及其总金额、退单的数量及其总金额。
  -- 注意：在您的查询中，给定月份和国家，忽略所有为零的行。
  
  Transactions 表：
  +------+---------+----------+--------+------------+
  | id   | country | state    | amount | trans_date |
  +------+---------+----------+--------+------------+
  | 101  | US      | approved | 1000   | 2019-05-18 |
  | 102  | US      | declined | 2000   | 2019-05-19 |
  | 103  | US      | approved | 3000   | 2019-06-10 |
  | 104  | US      | declined | 4000   | 2019-06-13 |
  | 105  | US      | approved | 5000   | 2019-06-15 |
  +------+---------+----------+--------+------------+
  
  Chargebacks 表：
  +------------+------------+
  | trans_id   | trans_date |
  +------------+------------+
  | 102        | 2019-05-29 |
  | 101        | 2019-06-30 |
  | 105        | 2019-09-18 |
  +------------+------------+
  
  Result 表：
  +----------+---------+----------------+-----------------+-------------------+--------------------+
  | month    | country | approved_count | approved_amount | chargeback_count  | chargeback_amount  |
  +----------+---------+----------------+-----------------+-------------------+--------------------+
  | 2019-05  | US      | 1              | 1000            | 1                 | 2000               |
  | 2019-06  | US      | 2              | 8000            | 1                 | 1000               |
  | 2019-09  | US      | 0              | 0               | 1                 | 5000               |
  +----------+---------+----------------+-----------------+-------------------+--------------------+
  
  
  -- 思路：找出所有state为approved和chargeback(自定义)再通过union联结起来，然后再通过SUM函数去求和
  
  SELECT
      DATE_FORMAT(trans_date, '%Y-%m') `month`,
      country,
      SUM(IF(state = 'approved', 1, 0)) approved_count,
      SUM(IF(state = 'approved', amount, 0)) approved_amount,
      SUM(IF(state = 'chargeback', 1, 0)) chargeback_count,
      SUM(IF(state = 'chargeback', amount, 0)) chargeback_amount
  FROM
      (
          SELECT
              trans_date, id, country, state, amount
          FROM
              Transactions
          UNION
          SELECT 
              c.trans_date, c.trans_id, t.country, 'chargeback' state, t.amount
          FROM
              Chargebacks c LEFT JOIN Transactions t
          ON c.trans_id = t.id
      ) t
  GROUP BY
      month, country
  HAVING 
      approved_count + chargeback_count > 0
  ```

- 查询球队积分

  ```sql
  -- 积分规则如下：
  -- 赢一场得三分；
  -- 平一场得一分；
  -- 输一场不得分。
  -- 写出一条SQL语句以查询每个队的 team_id，team_name 和 num_points。结果根据 num_points 降序排序，如果有两队积分相同，那么这两队按 team_id  升序排序。
  
  Teams table:
  +-----------+--------------+
  | team_id   | team_name    |
  +-----------+--------------+
  | 10        | Leetcode FC  |
  | 20        | NewYork FC   |
  | 30        | Atlanta FC   |
  | 40        | Chicago FC   |
  | 50        | Toronto FC   |
  +-----------+--------------+
  
  Matches table:
  +------------+--------------+---------------+-------------+--------------+
  | match_id   | host_team    | guest_team    | host_goals  | guest_goals  |
  +------------+--------------+---------------+-------------+--------------+
  | 1          | 10           | 20            | 3           | 0            |
  | 2          | 30           | 10            | 2           | 2            |
  | 3          | 10           | 50            | 5           | 1            |
  | 4          | 20           | 30            | 1           | 0            |
  | 5          | 50           | 30            | 1           | 0            |
  +------------+--------------+---------------+-------------+--------------+
  
  Result table:
  +------------+--------------+---------------+
  | team_id    | team_name    | num_points    |
  +------------+--------------+---------------+
  | 10         | Leetcode FC  | 7             |
  | 20         | NewYork FC   | 3             |
  | 50         | Toronto FC   | 3             |
  | 30         | Atlanta FC   | 1             |
  | 40         | Chicago FC   | 0             |
  +------------+--------------+---------------+
  
  
  -- 思路：
  -- 直接根据每一条数据来判断，如果当前id等于主场id，则判断分数并输出。。。
  
  SELECT
  	t.team_id, t.team_name, 
  SUM(
      CASE
      WHEN t.team_id = m1.host_team AND m1.host_goals > m1.guest_goals
      	THEN 3 
      WHEN m1.host_goals = m1.guest_goals
      	THEN 1 
      WHEN t.team_id = m1.guest_team AND m1.guest_goals > m1.host_goals
      	THEN 3 
      ELSE 0 
      END
  ) `num_points`
  FROM Teams t LEFT JOIN Matches m1
  ON t.team_id = m1.host_team OR t.team_id = m1.guest_team
  GROUP BY t.team_id
  ORDER BY num_points desc,team_id asc
  
  ```

- 页面推荐

  ```sql
  -- 向user_id = 1 的用户，推荐其朋友们喜欢的页面。不要推荐该用户已经喜欢的页面。
  -- 你返回的结果中不应当包含重复项。
  
  Friendship 表:
  +----------+----------+
  | user1_id | user2_id |
  +----------+----------+
  | 1        | 2        |
  | 1        | 3        |
  | 1        | 4        |
  | 2        | 3        |
  | 2        | 4        |
  | 2        | 5        |
  | 6        | 1        |
  +----------+----------+
   
  Likes 表:
  +---------+---------+
  | user_id | page_id |
  +---------+---------+
  | 1       | 88      |
  | 2       | 23      |
  | 3       | 24      |
  | 4       | 56      |
  | 5       | 11      |
  | 6       | 33      |
  | 2       | 77      |
  | 3       | 77      |
  | 6       | 88      |
  +---------+---------+
  
  Result 表:
  +------------------+
  | recommended_page |
  +------------------+
  | 23               |
  | 24               |
  | 56               |
  | 33               |
  | 77               |
  +------------------+
  -- 用户1 同 用户2, 3, 4, 6 是朋友关系。
  -- 推荐页面为： 页面23 来自于 用户2, 页面24 来自于 用户3, 页面56 来自于 用户3 以及 页面33 来自于 用户6。
  -- 页面77 同时被 用户2 和 用户3 推荐。
  -- 页面88 没有被推荐，因为 用户1 已经喜欢了它。
  
  --- 思路：直接找出用户1的所有朋友和用户1自己所关注的内容，然后自己进行筛选
  
  SELECT DISTINCT page_id recommended_page
  FROM Likes
  WHERE user_id IN 
  (
      SELECT DISTINCT user2_id id
      FROM Friendship
      WHERE user1_id = 1
      UNION 
      SELECT DISTINCT user1_id id
      FROM Friendship
      WHERE user2_id = 1
  ) 
  AND page_id NOT IN
  (
  	SELECT page_id
  	FROM Likes 
  	WHERE user_id = 1
  )
  
  ```

- 学生参加各科测试的次数

  ```
  要求写一段 SQL 语句，查询出每个学生参加每一门科目测试的次数，结果按 student_id 和 subject_name 排序。
  
  Students table:
  +------------+--------------+
  | student_id | student_name |
  +------------+--------------+
  | 1          | Alice        |
  | 2          | Bob          |
  | 13         | John         |
  | 6          | Alex         |
  +------------+--------------+
  Subjects table:
  +--------------+
  | subject_name |
  +--------------+
  | Math         |
  | Physics      |
  | Programming  |
  +--------------+
  Examinations table:
  +------------+--------------+
  | student_id | subject_name |
  +------------+--------------+
  | 1          | Math         |
  | 1          | Physics      |
  | 1          | Programming  |
  | 2          | Programming  |
  | 1          | Physics      |
  | 1          | Math         |
  | 13         | Math         |
  | 13         | Programming  |
  | 13         | Physics      |
  | 2          | Math         |
  | 1          | Math         |
  +------------+--------------+
  Result table:
  +------------+--------------+--------------+----------------+
  | student_id | student_name | subject_name | attended_exams |
  +------------+--------------+--------------+----------------+
  | 1          | Alice        | Math         | 3              |
  | 1          | Alice        | Physics      | 2              |
  | 1          | Alice        | Programming  | 1              |
  | 2          | Bob          | Math         | 1              |
  | 2          | Bob          | Physics      | 0              |
  | 2          | Bob          | Programming  | 1              |
  | 6          | Alex         | Math         | 0              |
  | 6          | Alex         | Physics      | 0              |
  | 6          | Alex         | Programming  | 0              |
  | 13         | John         | Math         | 1              |
  | 13         | John         | Physics      | 1              |
  | 13         | John         | Programming  | 1              |
  +------------+--------------+--------------+----------------+
  结果表需包含所有学生和所有科目（即便测试次数为0）：
  Alice 参加了 3 次数学测试, 2 次物理测试，以及 1 次编程测试；
  Bob 参加了 1 次数学测试, 1 次编程测试，没有参加物理测试；
  Alex 啥测试都没参加；
  John  参加了数学、物理、编程测试各 1 次。
  
  select  t1.student_id, 
          t1.student_name, t1.subject_name, 
          case when isnull(t2.total) then 0 else t2.total end as attended_exams
  from
      (select *
      from Students cross join Subjects)
      t1
      left join
      (select student_id, subject_name, count(*) total
      from Examinations 
      group by student_id, subject_name)
      t2
      using(student_id, subject_name)
  order by
      student_id, subject_name
      
  运用Students和Subjects的笛卡尔积(cross-join), 也就是两两匹配啦, 得到我们的prototype result table, 命名为t1
  在examinations表中用group + agg, 计算出学生参加某个学科的次数, 命名为t2
  用t1 left join t2, 就把t2的考试场次信息, expand到t1上了, 但是t1中还有一些null值, 即(学生, 学科)pair在examinations表中并不存在, 那么, 我们需要把null转化为0, 用if else或者case when即可
  最后, 按题意, 应该order by student_id, subject_name
  综上, 此题考查的是连接的思想, cross join的目的是得到所有的可能的pair, left join是把一个表的信息expand到另外一个表.
  
  
  ```

  

- 向CEO汇报工作的所有人

  ```sql
  -- 用 SQL 查询出所有直接或间接向公司 CEO 汇报工作的职工的 employee_id 。
  -- 由于公司规模较小，经理之间的间接关系不超过 3 个经理。
  -- 可以以任何顺序返回的结果，不需要去重。
  
  Employees 表:
  +-------------+---------------+------------+
  | employee_id | employee_name | manager_id |
  +-------------+---------------+------------+
  | 1           | Boss          | 1          |
  | 3           | Alice         | 3          |
  | 2           | Bob           | 1          |
  | 4           | Daniel        | 2          |
  | 7           | Luis          | 4          |
  | 8           | Jhon          | 3          |
  | 9           | Angela        | 8          |
  | 77          | Robert        | 1          |
  +-------------+---------------+------------+
  
  Result 表:
  +-------------+
  | employee_id |
  +-------------+
  | 2           |
  | 77          |
  | 4           |
  | 7           |
  +-------------+
  
  -- 公司 CEO 的 employee_id 是 1.
  -- employee_id 是 2 和 77 的职员直接汇报给公司 CEO。
  -- employee_id 是 4 的职员间接汇报给公司 CEO 4 --> 2 --> 1 。
  -- employee_id 是 7 的职员间接汇报给公司 CEO 7 --> 4 --> 2 --> 1 。
  -- employee_id 是 3, 8 ，9 的职员不会直接或间接的汇报给公司 CEO。 
  
  -- 思路：
  
  SELECCT
  	a.employee_id EMPLOYEE_ID
  FROM 
      Employees a 
      LEFT JOIN
      Employees b ON a.manager_id = b.employee_id
      LEFT JOIN
      Employees c ON b.manager_id = c.employee_id
      LEFT JOIN
      Employees d ON c.manager_id = d.employee_id
  WHERE 
      a.employee_id != 1 AND d.employee_id = 1;
  ```

- 找到连续区间的开始和结束数字

  ```sql
  -- 编写一个 SQL 查询得到 Logs 表中的连续区间的开始数字和结束数字。
  
  Logs 表：
  +------------+
  | log_id     |
  +------------+
  | 1          |
  | 2          |
  | 3          |
  | 7          |
  | 8          |
  | 10         |
  +------------+
  
  结果表：
  +------------+--------------+
  | start_id   | end_id       |
  +------------+--------------+
  | 1          | 3            |
  | 7          | 8            |
  | 10         | 10           |
  +------------+--------------+
  结果表应包含 Logs 表中的所有区间。
  从 1 到 3 在表中。
  从 4 到 6 不在表中。
  从 7 到 8 在表中。
  9 不在表中。
  10 在表中。
  
  -- 思路：
  
  SELECT min(log_id) start_id, max(log_id) end_id
  FROM
  (
  	SELECT log_id,
  		CASE
  		WHEN @id = log_id - 1 THEN @num := @num
  		ELSE @num := @num + 1
  		END num,
  		@id := log_id
  	FROM LOGS, (SELECT @num := 0, @id := NULL) a
  ) x
  GROUP BY num
  ```

- 不同国家的天气类型

  ```sql
  -- 写一段 SQL 来找到表中每个国家在 2019 年 11 月的天气类型。
  -- 天气类型的定义如下：当 weather_state 的平均值小于或等于15返回 Cold，当 weather_state 的平均值大于或等于 25 返回 Hot，否则返回 Warm。
  -- 你可以以任意顺序返回你的查询结果。
  
  Countries 表:
  +------------+--------------+
  | country_id | country_name |
  +------------+--------------+
  | 2          | USA          |
  | 3          | Australia    |
  | 7          | Peru         |
  | 5          | China        |
  | 8          | Morocco      |
  | 9          | Spain        |
  +------------+--------------+
  Weather 表:
  +------------+---------------+------------+
  | country_id | weather_state | day        |
  +------------+---------------+------------+
  | 2          | 15            | 2019-11-01 |
  | 2          | 12            | 2019-10-28 |
  | 2          | 12            | 2019-10-27 |
  | 3          | -2            | 2019-11-10 |
  | 3          | 0             | 2019-11-11 |
  | 3          | 3             | 2019-11-12 |
  | 5          | 16            | 2019-11-07 |
  | 5          | 18            | 2019-11-09 |
  | 5          | 21            | 2019-11-23 |
  | 7          | 25            | 2019-11-28 |
  | 7          | 22            | 2019-12-01 |
  | 7          | 20            | 2019-12-02 |
  | 8          | 25            | 2019-11-05 |
  | 8          | 27            | 2019-11-15 |
  | 8          | 31            | 2019-11-25 |
  | 9          | 7             | 2019-10-23 |
  | 9          | 3             | 2019-12-23 |
  +------------+---------------+------------+
  Result 表:
  +--------------+--------------+
  | country_name | weather_type |
  +--------------+--------------+
  | USA          | Cold         |
  | Austraila    | Cold         |
  | Peru         | Hot          |
  | China        | Warm         |
  | Morocco      | Hot          |
  +--------------+--------------+
  USA 11 月的平均 weather_state 为 (15) / 1 = 15 所以天气类型为 Cold。
  Australia 11 月的平均 weather_state 为 (-2 + 0 + 3) / 3 = 0.333 所以天气类型为 Cold。
  Peru 11 月的平均 weather_state 为 (25) / 1 = 25 所以天气类型为 Hot。
  China 11 月的平均 weather_state 为 (16 + 18 + 21) / 3 = 18.333 所以天气类型为 Warm。
  Morocco 11 月的平均 weather_state 为 (25 + 27 + 31) / 3 = 27.667 所以天气类型为 Hot。
  我们并不知道 Spain 在 11 月的 weather_state 情况所以无需将他包含在结果中。
  
  
  select country_name, 
          case 
              when avg(weather_state) <= 15 then 'Cold'
              when avg(weather_state) >= 25 then 'Hot'
              else 'Warm'
          end
          as weather_type
  from Countries, Weather 
  where Countries.country_id = Weather.country_id and day between '2019-11-01' and '2019-11-30'
  group by Countries.country_id
  ```

- 不同性别每日分数统计

  ```sql
  -- 查询每种性别在每一天的总分，并按性别和日期对查询结果排序
  
  Scores表:
  +-------------+--------+------------+--------------+
  | player_name | gender | day        | score_points |
  +-------------+--------+------------+--------------+
  | Aron        | F      | 2020-01-01 | 17           |
  | Alice       | F      | 2020-01-07 | 23           |
  | Bajrang     | M      | 2020-01-07 | 7            |
  | Khali       | M      | 2019-12-25 | 11           |
  | Slaman      | M      | 2019-12-30 | 13           |
  | Joe         | M      | 2019-12-31 | 3            |
  | Jose        | M      | 2019-12-18 | 2            |
  | Priya       | F      | 2019-12-31 | 23           |
  | Priyanka    | F      | 2019-12-30 | 17           |
  +-------------+--------+------------+--------------+
  结果表:
  +--------+------------+-------+
  | gender | day        | total |
  +--------+------------+-------+
  | F      | 2019-12-30 | 17    |
  | F      | 2019-12-31 | 40    |
  | F      | 2020-01-01 | 57    |
  | F      | 2020-01-07 | 80    |
  | M      | 2019-12-18 | 2     |
  | M      | 2019-12-25 | 13    |
  | M      | 2019-12-30 | 26    |
  | M      | 2019-12-31 | 29    |
  | M      | 2020-01-07 | 36    |
  +--------+------------+-------+
  -- 女性队伍:
  -- 第一天是 2019-12-30，Priyanka 获得 17 分，队伍的总分是 17 分
  -- 第二天是 2019-12-31, Priya 获得 23 分，队伍的总分是 40 分
  -- 第三天是 2020-01-01, Aron 获得 17 分，队伍的总分是 57 分
  -- 第四天是 2020-01-07, Alice 获得 23 分，队伍的总分是 80 分
  -- 男性队伍：
  -- 第一天是 2019-12-18, Jose 获得 2 分，队伍的总分是 2 分
  -- 第二天是 2019-12-25, Khali 获得 11 分，队伍的总分是 13 分
  -- 第三天是 2019-12-30, Slaman 获得 13 分，队伍的总分是 26 分
  -- 第四天是 2019-12-31, Joe 获得 3 分，队伍的总分是 29 分
  -- 第五天是 2020-01-07, Bajrang 获得 7 分，队伍的总分是 36 分
  
  -- 思路：进行自联结，联结条件为t1的日期大于t2的日期并且两个表的性别相同，此时t2的记录均为满足计算条件，此时再进行分组，分组条件为t1的日期和性别
  
  select s.gender, s.day, sum(s1.score_points) total
  from Scores s left join scores s1
  on s.day >= s1.day and s.gender = s1.gender
  group by s.gender, s.day
  order by s.gender, s.day
  ```

- 餐馆营业额变化增长

  ```sql
  -- 查询计算以 7 天（某日期 + 该日期前的 6 天）为一个时间段的顾客消费平均值
  -- 查询结果格式的例子如下：
  -- 查询结果按 visited_on 排序
  -- average_amount 要 保留两位小数，日期数据的格式为 ('YYYY-MM-DD')
  
  Customer 表:
  +-------------+--------------+--------------+-------------+
  | customer_id | name         | visited_on   | amount      |
  +-------------+--------------+--------------+-------------+
  | 1           | Jhon         | 2019-01-01   | 100         |
  | 2           | Daniel       | 2019-01-02   | 110         |
  | 3           | Jade         | 2019-01-03   | 120         |
  | 4           | Khaled       | 2019-01-04   | 130         |
  | 5           | Winston      | 2019-01-05   | 110         | 
  | 6           | Elvis        | 2019-01-06   | 140         | 
  | 7           | Anna         | 2019-01-07   | 150         |
  | 8           | Maria        | 2019-01-08   | 80          |
  | 9           | Jaze         | 2019-01-09   | 110         | 
  | 1           | Jhon         | 2019-01-10   | 130         | 
  | 3           | Jade         | 2019-01-10   | 150         | 
  +-------------+--------------+--------------+-------------+
  
  结果表:
  +--------------+--------------+----------------+
  | visited_on   | amount       | average_amount |
  +--------------+--------------+----------------+
  | 2019-01-07   | 860          | 122.86         |
  | 2019-01-08   | 840          | 120            |
  | 2019-01-09   | 840          | 120            |
  | 2019-01-10   | 1000         | 142.86         |
  +--------------+--------------+----------------+
  
  -- 第一个七天消费平均值从 2019-01-01 到 2019-01-07 是 (100 + 110 + 120 + 130 + 110 + 140 + 150)/7 = 122.86
  -- 第二个七天消费平均值从 2019-01-02 到 2019-01-08 是 (110 + 120 + 130 + 110 + 140 + 150 + 80)/7 = 120
  -- 第三个七天消费平均值从 2019-01-03 到 2019-01-09 是 (120 + 130 + 110 + 140 + 150 + 80 + 110)/7 = 120
  -- 第四个七天消费平均值从 2019-01-04 到 2019-01-10 是 (130 + 110 + 140 + 150 + 80 + 110 + 130 + 150)/7 = 142.86
  
  -- 思路：
  
  SELECT
  	a.visited_on,
  	sum( b.amount ) AS amount,
  	round(sum( b.amount ) / 7, 2 ) AS average_amount 
  FROM
  	( SELECT DISTINCT visited_on FROM customer ) a JOIN customer b 
   	ON datediff( a.visited_on, b.visited_on ) BETWEEN 0 AND 6 
  WHERE
  	a.visited_on >= ( SELECT min( visited_on ) FROM customer ) + 6 
  GROUP BY
  	a.visited_on
  ```

- 活动参与者

  ```sql
  -- 查询那些既没有最多，也没有最少参与者的活动的名字
  -- 可以以任何顺序返回结果，Activities 表的每项活动的参与者都来自 Friends 表
  
  Friends 表:
  +------+--------------+---------------+
  | id   | name         | activity      |
  +------+--------------+---------------+
  | 1    | Jonathan D.  | Eating        |
  | 2    | Jade W.      | Singing       |
  | 3    | Victor J.    | Singing       |
  | 4    | Elvis Q.     | Eating        |
  | 5    | Daniel A.    | Eating        |
  | 6    | Bob B.       | Horse Riding  |
  +------+--------------+---------------+
  
  Activities 表:
  +------+--------------+
  | id   | name         |
  +------+--------------+
  | 1    | Eating       |
  | 2    | Singing      |
  | 3    | Horse Riding |
  +------+--------------+
  
  Result 表:
  +--------------+
  | activity     |
  +--------------+
  | Singing      |
  +--------------+
  
  -- Eating 活动有三个人参加, 是最多人参加的活动 (Jonathan D. , Elvis Q. and Daniel A.)
  -- Horse Riding 活动有一个人参加, 是最少人参加的活动 (Bob B.)
  -- Singing 活动有两个人参加 (Victor J. and Jade W.)
  
  -- 思路：掐头去尾
  
  select activity
  from Friends
  group by activity
  having count(id) != (select count(id) from Friends group by activity order by count(id) desc limit 1)
  and count(id) != (select count(id) from Friends group by activity order by count(id) limit 1)
  ```

- 可信联系人的数量

  ```sql
  -- 为每张发票 invoice_id 编写一个SQL查询以查找以下内容：
  -- customer_name：与发票相关的顾客名称。
  -- price：发票的价格。
  -- contacts_cnt：该顾客的联系人数量。
  -- trusted_contacts_cnt：可信联系人的数量：既是该顾客的联系人又是商店顾客的联系人数量（即：可信联系人的电子邮件存在于客户表中）。
  -- 将查询的结果按照 invoice_id 排序。
  
  
  Customers table:
  +-------------+---------------+--------------------+
  | customer_id | customer_name | email              |
  +-------------+---------------+--------------------+
  | 1           | Alice         | alice@leetcode.com |
  | 2           | Bob           | bob@leetcode.com   |
  | 13          | John          | john@leetcode.com  |
  | 6           | Alex          | alex@leetcode.com  |
  +-------------+---------------+--------------------+
  Contacts table:
  +-------------+--------------+--------------------+
  | user_id     | contact_name | contact_email      |
  +-------------+--------------+--------------------+
  | 1           | Bob          | bob@leetcode.com   |
  | 1           | John         | john@leetcode.com  |
  | 1           | Jal          | jal@leetcode.com   |
  | 2           | Omar         | omar@leetcode.com  |
  | 2           | Meir         | meir@leetcode.com  |
  | 6           | Alice        | alice@leetcode.com |
  +-------------+--------------+--------------------+
  Invoices table:
  +------------+-------+---------+
  | invoice_id | price | user_id |
  +------------+-------+---------+
  | 77         | 100   | 1       |
  | 88         | 200   | 1       |
  | 99         | 300   | 2       |
  | 66         | 400   | 2       |
  | 55         | 500   | 13      |
  | 44         | 60    | 6       |
  +------------+-------+---------+
  Result table:
  +------------+---------------+-------+--------------+----------------------+
  | invoice_id | customer_name | price | contacts_cnt | trusted_contacts_cnt |
  +------------+---------------+-------+--------------+----------------------+
  | 44         | Alex          | 60    | 1            | 1                    |
  | 55         | John          | 500   | 0            | 0                    |
  | 66         | Bob           | 400   | 2            | 0                    |
  | 77         | Alice         | 100   | 3            | 2                    |
  | 88         | Alice         | 200   | 3            | 2                    |
  | 99         | Bob           | 300   | 2            | 0                    |
  +------------+---------------+-------+--------------+----------------------+
  -- Alice 有三位联系人，其中两位(Bob 和 John)是可信联系人。
  -- Bob 有两位联系人, 他们中的任何一位都不是可信联系人。
  -- Alex 只有一位联系人(Alice)，并是一位可信联系人。
  -- John 没有任何联系人。
  
  
  select i.invoice_id, c1.customer_name, i.price, 
      count(ct.contact_name) contacts_cnt ,
      count(c2.customer_name) trusted_contacts_cnt 
  from invoices i join customers c1 on i.user_id = c1.customer_id
      left join contacts ct on i.user_id = ct.user_id
      left join customers c2 on ct.contact_email = c2.email
  group by i.invoice_id
  order by i.invoice_id
  ```

- 股票的资本损益

  ```sql
  -- 编写一个SQL查询来报告每支股票的资本损益。
  -- 股票的资本损益是一次或多次买卖股票后的全部收益或损失。
  -- 以任意顺序返回结果即可。
  
  Stocks 表:
  +---------------+-----------+---------------+--------+
  | stock_name    | operation | operation_day | price  |
  +---------------+-----------+---------------+--------+
  | Leetcode      | Buy       | 1             | 1000   |
  | Corona Masks  | Buy       | 2             | 10     |
  | Leetcode      | Sell      | 5             | 9000   |
  | Handbags      | Buy       | 17            | 30000  |
  | Corona Masks  | Sell      | 3             | 1010   |
  | Corona Masks  | Buy       | 4             | 1000   |
  | Corona Masks  | Sell      | 5             | 500    |
  | Corona Masks  | Buy       | 6             | 1000   |
  | Handbags      | Sell      | 29            | 7000   |
  | Corona Masks  | Sell      | 10            | 10000  |
  +---------------+-----------+---------------+--------+
  
  Result 表:
  +---------------+-------------------+
  | stock_name    | capital_gain_loss |
  +---------------+-------------------+
  | Corona Masks  | 9500              |
  | Leetcode      | 8000              |
  | Handbags      | -23000            |
  +---------------+-------------------+
  -- Leetcode 股票在第一天以1000美元的价格买入，在第五天以9000美元的价格卖出。资本收益=9000-1000=8000美元。
  -- Handbags 股票在第17天以30000美元的价格买入，在第29天以7000美元的价格卖出。资本损失=7000-30000=-23000美元。
  -- Corona Masks 股票在第1天以10美元的价格买入，在第3天以1010美元的价格卖出。在第4天以1000美元的价格再次购买，在第5天以500美元的价格出售。最后，它在第6天以1000美元的价格被买走，在第10天以10000美元的价格被卖掉。资本损益是每次（’Buy'->'Sell'）操作资本收益或损失的和=（1010-10）+（500-1000）+（10000-1000）=1000-500+9000=9500美元。
  
  -- 思路：
  
  SELECT stock_name, SUM(IF(operation='Sell', price, -price)) AS capital_gain_loss
  FROM Stocks
  GROUP BY stock_name
  ```

- 购买了产品A和产品B却没有购买产品C的顾客

  ```sql
  -- 请你设计 SQL 查询来报告购买了产品 A 和产品 B 却没有购买产品 C 的顾客的 ID 和姓名（ customer_id 和 customer_name ），我们将基于此结果为他们推荐产品 C 。
  -- 您返回的查询结果需要按照 customer_id 排序。
  
  Customers 表:
  +-------------+---------------+
  | customer_id | customer_name |
  +-------------+---------------+
  | 1           | Daniel        |
  | 2           | Diana         |
  | 3           | Elizabeth     |
  | 4           | Jhon          |
  +-------------+---------------+
  
  Orders 表:
  +------------+--------------+---------------+
  | order_id   | customer_id  | product_name  |
  +------------+--------------+---------------+
  | 10         |     1        |     A         |
  | 20         |     1        |     B         |
  | 30         |     1        |     D         |
  | 40         |     1        |     C         |
  | 50         |     2        |     A         |
  | 60         |     3        |     A         |
  | 70         |     3        |     B         |
  | 80         |     3        |     D         |
  | 90         |     4        |     C         |
  +------------+--------------+---------------+
  
  Result 表:
  +-------------+---------------+
  | customer_id | customer_name |
  +-------------+---------------+
  | 3           | Elizabeth     |
  +-------------+---------------+
  
  -- 只有 customer_id 为 3 的顾客购买了产品 A 和产品 B ，却没有购买产品 C 。
  
  -- 思路：
  
  select c.customer_id,  customer_name
  from customers c join orders o
  on c.customer_id = o.customer_id
  group by c.customer_id
  having sum(product_name = 'A') > 0 and sum(product_name = 'B') > 0 and sum(product_name = 'C') = 0
  ```

- 制作会话柱状图

  ```sql
  -- 你想知道用户在你的 app 上的访问时长情况。因此决定统计访问时长区间分别为 "[0-5>", "[5-10>", "[10-15>" 和 "15 or more" （单位：分钟）的会话数量，并以此绘制柱状图。
  -- 写一个SQL查询来报告（访问时长区间，会话总数）。结果可用任何顺序呈现。
  
  Sessions 表：
  +-------------+---------------+
  | session_id  | duration      |
  +-------------+---------------+
  | 1           | 30            |
  | 2           | 199           |
  | 3           | 299           |
  | 4           | 580           |
  | 5           | 1000          |
  +-------------+---------------+
  
  Result 表：
  +--------------+--------------+
  | bin          | total        |
  +--------------+--------------+
  | [0-5>        | 3            |
  | [5-10>       | 1            |
  | [10-15>      | 0            |
  | 15 or more   | 1            |
  +--------------+--------------+
  
  -- 对于 session_id 1，2 和 3 ，它们的访问时间大于等于 0 分钟且小于 5 分钟。
  -- 对于 session_id 4，它的访问时间大于等于 5 分钟且小于 10 分钟。
  -- 没有会话的访问时间大于等于 10 分钟且小于 15 分钟。
  -- 对于 session_id 5, 它的访问时间大于等于 15 分钟。
  
  -- 思路：
  
  
  select '[0-5>' as bin, count(*) as total from Sessions where duration/60>=0 and duration/60<5
  union
  select '[5-10>' as bin, count(*) as total from Sessions where duration/60>=5 and duration/60<10
  union
  select '[10-15>' as bin, count(*) as total from Sessions where duration/60>=10 and duration/60<15
  union
  select '15 or more'as bin, count(*) as total from Sessions where duration/60>=15
  ```

- 计算布尔表达式的值

  ```sql
  -- 写一个 SQL 查询,  以计算表 Expressions 中的布尔表达式.
  
  Variables 表:
  +------+-------+
  | name | value |
  +------+-------+
  | x    | 66    |
  | y    | 77    |
  +------+-------+
  
  Expressions 表:
  +--------------+----------+---------------+
  | left_operand | operator | right_operand |
  +--------------+----------+---------------+
  | x            | >        | y             |
  | x            | <        | y             |
  | x            | =        | y             |
  | y            | >        | x             |
  | y            | <        | x             |
  | x            | =        | x             |
  +--------------+----------+---------------+
  
  Result 表:
  +--------------+----------+---------------+-------+
  | left_operand | operator | right_operand | value |
  +--------------+----------+---------------+-------+
  | x            | >        | y             | false |
  | x            | <        | y             | true  |
  | x            | =        | y             | false |
  | y            | >        | x             | true  |
  | y            | <        | x             | false |
  | x            | =        | x             | true  |
  +--------------+----------+---------------+-------+
  -- 如上所示, 你需要通过使用 Variables 表来找到 Expressions 表中的每一个布尔表达式的值.
  
  -- 思路
  
  select e.left_operand,e.operator,e.right_operand,
  case e.operator
      when '>' then if(v1.value > v2.value, 'true', 'false')
      when '<' then if(v1.value < v2.value, 'true', 'false')
      else  if(v1.value=v2.value, 'true', 'false')
  end value
  from Expressions e
  left join Variables v1 on v1.name = e.left_operand 
  left join Variables v2 on v2.name = e.right_operand
  ```

- 苹果和桔子

  ```sql
  -- 写一个 SQL 查询, 报告每一天 苹果 和 桔子 销售的数目的差异.
  -- 返回的结果表, 按照格式为 ('YYYY-MM-DD') 的 sale_date 排序.
  
  Sales 表:
  +------------+------------+-------------+
  | sale_date  | fruit      | sold_num    |
  +------------+------------+-------------+
  | 2020-05-01 | apples     | 10          |
  | 2020-05-01 | oranges    | 8           |
  | 2020-05-02 | apples     | 15          |
  | 2020-05-02 | oranges    | 15          |
  | 2020-05-03 | apples     | 20          |
  | 2020-05-03 | oranges    | 0           |
  | 2020-05-04 | apples     | 15          |
  | 2020-05-04 | oranges    | 16          |
  +------------+------------+-------------+
  
  Result 表:
  +------------+--------------+
  | sale_date  | diff         |
  +------------+--------------+
  | 2020-05-01 | 2            |
  | 2020-05-02 | 0            |
  | 2020-05-03 | 20           |
  | 2020-05-04 | -1           |
  +------------+--------------+
  
  -- 在 2020-05-01, 卖了 10 个苹果 和 8 个桔子 (差异为 10 - 8 = 2).
  -- 在 2020-05-02, 卖了 15 个苹果 和 15 个桔子 (差异为 15 - 15 = 0).
  -- 在 2020-05-03, 卖了 20 个苹果 和 0 个桔子 (差异为 20 - 0 = 20).
  -- 在 2020-05-04, 卖了 15 个苹果 和 16 个桔子 (差异为 15 - 16 = -1).
  
  
  -- 思路
  
  SELECT t1.sale_date ,t1.sold_num - t2.sold_num AS diff
  FROM Sales t1 join Sales t2
  on t1.sale_date = t2.sale_date AND t1.fruit = 'apples' AND t2.fruit = 'oranges'
  ```

- 活跃用户

  ```sql
  -- 写一个 SQL 查询,  找到活跃用户的 id 和 name.
  -- 活跃用户是指那些至少连续 5 天登录账户的用户.
  -- 返回的结果表按照 id 排序.
  
  Accounts 表:
  +----+----------+
  | id | name     |
  +----+----------+
  | 1  | Winston  |
  | 7  | Jonathan |
  +----+----------+
  
  Logins 表:
  +----+------------+
  | id | login_date |
  +----+------------+
  | 7  | 2020-05-30 |
  | 1  | 2020-05-30 |
  | 7  | 2020-05-31 |
  | 7  | 2020-06-01 |
  | 7  | 2020-06-02 |
  | 7  | 2020-06-02 |
  | 7  | 2020-06-03 |
  | 1  | 2020-06-07 |
  | 7  | 2020-06-10 |
  +----+------------+
  
  Result 表:
  +----+----------+
  | id | name     |
  +----+----------+
  | 7  | Jonathan |
  +----+----------+
  
  id = 1 的用户 Winston 仅仅在不同的 2 天内登录了 2 次, 所以, Winston 不是活跃用户.
  id = 7 的用户 Jonathon 在不同的 6 天内登录了 7 次, , 6 天中有 5 天是连续的, 所以, Jonathan 是活跃用户.
  
  -- 思路
  
  SELECT
      DISTINCT
      b.id,
      b.name
  FROM
      (SELECT
          id,
          CASE 
          WHEN @id = id AND @prev_date = subdate(login_date, interval 1 day) AND (@prev_date:= login_date) IS NOT NULL
              THEN @cnt := @cnt+1
          WHEN @id = id AND @prev_date = subdate(login_date, interval 0 day)
              THEN @cnt := @cnt
          WHEN (@id := id) IS NOT NULL AND(@prev_date := login_date) IS NOT NULL
              THEN @cnt := 1 
          END AS cnt
      FROM 
          (SELECT * FROM logins ORDER BY id, login_date) a,
          (SELECT @prev_date := NULL, @cnt := NULL, @id := NULL) t
      ) a
      LEFT JOIN accounts b ON a.id = b.id
  WHERE 
      a.cnt >= 5
  ```

- 矩形面积

  ```sql
  -- 写一个 SQL 语句, 报告由表中任意两点可以形成的所有可能的矩形. 
  -- 结果表中的每一行包含三列 (p1, p2, area) 如下:
  -- p1 和 p2 是矩形两个对角的 id 且 p1 < p2.
  -- 矩形的面积由列 area 表示. 
  -- 请按照面积大小降序排列，如果面积相同的话, 则按照 p1 和 p2 升序对结果表排序
  
  Points 表:
  +----------+-------------+-------------+
  | id       | x_value     | y_value     |
  +----------+-------------+-------------+
  | 1        | 2           | 8           |
  | 2        | 4           | 7           |
  | 3        | 2           | 10          |
  +----------+-------------+-------------+
  
  Result 表:
  +----------+-------------+-------------+
  | p1       | p2          | area        |
  +----------+-------------+-------------+
  | 2        | 3           | 6           |
  | 1        | 2           | 2           |
  +----------+-------------+-------------+
  
  -- p1 应该小于 p2 并且面积大于 0.
  -- p1 = 1 且 p2 = 2 时, 面积等于 |2-4| * |8-7| = 2.
  -- p1 = 2 且 p2 = 3 时, 面积等于 |4-2| * |7-10| = 6.
  -- p1 = 1 且 p2 = 3 时, 是不可能为矩形的, 因为面积等于 0.
  
  -- 思路：
  
  select pt1.id p1, pt2.id p2, abs((pt1.x_value - pt2.x_value) * (pt1.y_value - pt2.y_value)) area
  from points pt1 join points pt2 on pt1.id < pt2.id 
  where pt1.x_value != pt2.x_value and pt1.y_value != pt2.y_value 
  order by area desc, p1, p2
  ```

- 计算税后工资

  ```sql
  -- 写一条查询 SQL 来查找每个员工的税后工资
  -- 每个公司的税率计算依照以下规则
  -- 如果这个公司员工最高工资不到 1000 ，税率为 0%
  -- 如果这个公司员工最高工资在 1000 到 10000 之间，税率为 24%
  -- 如果这个公司员工最高工资大于 10000 ，税率为 49%
  -- 按任意顺序返回结果，税后工资结果取整
  
  Salaries 表：
  +------------+-------------+---------------+--------+
  | company_id | employee_id | employee_name | salary |
  +------------+-------------+---------------+--------+
  | 1          | 1           | Tony          | 2000   |
  | 1          | 2           | Pronub        | 21300  |
  | 1          | 3           | Tyrrox        | 10800  |
  | 2          | 1           | Pam           | 300    |
  | 2          | 7           | Bassem        | 450    |
  | 2          | 9           | Hermione      | 700    |
  | 3          | 7           | Bocaben       | 100    |
  | 3          | 2           | Ognjen        | 2200   |
  | 3          | 13          | Nyancat       | 3300   |
  | 3          | 15          | Morninngcat   | 1866   |
  +------------+-------------+---------------+--------+
  
  Result 表：
  +------------+-------------+---------------+--------+
  | company_id | employee_id | employee_name | salary |
  +------------+-------------+---------------+--------+
  | 1          | 1           | Tony          | 1020   |
  | 1          | 2           | Pronub        | 10863  |
  | 1          | 3           | Tyrrox        | 5508   |
  | 2          | 1           | Pam           | 300    |
  | 2          | 7           | Bassem        | 450    |
  | 2          | 9           | Hermione      | 700    |
  | 3          | 7           | Bocaben       | 76     |
  | 3          | 2           | Ognjen        | 1672   |
  | 3          | 13          | Nyancat       | 2508   |
  | 3          | 15          | Morninngcat   | 5911   |
  +------------+-------------+---------------+--------+
  -- 对于公司 1 ，最高工资是 21300 ，其每个员工的税率为 49%
  -- 对于公司 2 ，最高工资是 700 ，其每个员工税率为 0%
  -- 对于公司 3 ，最高工资是 7777 ，其每个员工税率是 24%
  -- 税后工资计算 = 工资 - ( 税率 / 100）*工资
  -- 对于上述案例，Morninngcat 的税后工资 = 7777 - 7777 * ( 24 / 100) = 7777 - 1866.48 = 5910.52 ，取整为 5911
  
  
  -- 思路：
  
  select S.company_id, S.employee_id, S.employee_name, round(S.salary * (1 - T.tax), 0) as salary
  from Salaries as S
  left join (
              select company_id,
              (case
              when max(salary) < 1000 then 0
              when max(salary) between 1000 and 10000 then 0.24
              when max(salary) > 10000 then 0.49
              end) as tax
              from Salaries
              group by company_id
  ) as T 
  on S.company_id = T.company_id
  ```

- 按日期分组销售产品

  ```sql
  编写一个 SQL 查询来查找每个日期、销售的不同产品的数量及其名称。
  每个日期的销售产品名称应按词典序排列。
  返回按 sell_date 排序的结果表。
  
  Activities 表：
  +------------+-------------+
  | sell_date  | product     |
  +------------+-------------+
  | 2020-05-30 | Headphone   |
  | 2020-06-01 | Pencil      |
  | 2020-06-02 | Mask        |
  | 2020-05-30 | Basketball  |
  | 2020-06-01 | Bible       |
  | 2020-06-02 | Mask        |
  | 2020-05-30 | T-Shirt     |
  +------------+-------------+
  
  Result 表：
  +------------+----------+------------------------------+
  | sell_date  | num_sold | products                     |
  +------------+----------+------------------------------+
  | 2020-05-30 | 3        | Basketball,Headphone,T-shirt |
  | 2020-06-01 | 2        | Bible,Pencil                 |
  | 2020-06-02 | 1        | Mask                         |
  +------------+----------+------------------------------+
  对于2020-05-30，出售的物品是 (Headphone, Basketball, T-shirt)，按词典序排列，并用逗号 ',' 分隔。
  对于2020-06-01，出售的物品是 (Pencil, Bible)，按词典序排列，并用逗号分隔。
  对于2020-06-02，出售的物品是 (Mask)，只需返回该物品名。
  
  select sell_date, count(distinct product) as num_sold,group_concat(distinct product) as products 
  from Activities
  group by sell_date
  ```

  

- 

- 

- 

- 

- 









## 常用函数

- `IFNULL(expr1，expr2)`， 如果`expr1`不为空则返回`expr1`，为空则返回`expr2`

- `IF(expr1，expr2，expr3)`，如果expr1为真，则返回expr2，为假则返回expr3

- `DATADIFF(time1，time2)`,  返回time1 - time2

- `CASE WHEN`,

  1. `CASE expr1 WHEN expr2 THEN expr3 WHEN expr4 THEN expr5 ELSE expr6 END`，如果expr1等于expr2返回expr3，等于expr4返回expr5，否则返回expr6
  2. `CASE WHEN expr2 THEN expr3 WHEN expr4 THEN expr5 END`，如果expr2为真则返回expr3，如果expr4为真则返回expr5

- `DATE_FORMAT(time, '%Y-%m')`，将time转换成 年-月 格式

- 自定义函数

  ```sql
  -- 自定义一个函数，返回第n高的数，如果不存在则返回null
  
  CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
  BEGIN
      SET N := N-1;
    RETURN (
        SELECT 
              salary
        FROM 
              employee
        GROUP BY 
              salary
        ORDER BY 
              salary DESC
        LIMIT N, 1
    );
  END
  ```
  
- 窗口函数

  ```
  
  ```

- 



## 神奇操作

- `SUM(expr)`， 求出expr为真时的数目，等价于 `COUNT(IF(expr, 1, 0))`
- `AVG(statement is not null)`,求出statement中不为空的数占所有数的比例
- `time + 1`，表示time的后一天
- `COUNT`不会计算控制
- `SELECT 'value' column_name` 直接输出一个2*1的表





## 特别注意

- `‘’(空值)`和`NULL`的区别

  1. 空值不占空间，NULL值占空间；
  2. 字段限制条件为`NOT NULL`时可以插入空值；
  3. `IS NOT NULL`只可以过滤`null`，`<> ''`可以同时过滤空值和`NULL`；
  4. `COUNT`统计时会过滤掉 `NULL` 值，但是不会过滤掉空值；

- 出现空值和`NULL`的情况

  ```sql
  -- 下面的查询如果不存在满足条件的记录时会输出空值
  SELECT num
  FROM my_numbers
  GROUP BY num
  HAVING COUNT(*) = 3
  ORDER BY num DESC
  limit 1
  
  
  -- 如果从空值中查找最大值会输出NULL，如下
  SELECT
      MAX(num) AS num
  FROM
  (
      SELECT num
      FROM my_numbers
      GROUP BY num
      HAVING COUNT(num) = 1
  ) AS t
  ```

- 





## 格式规范

```sql
SELECT
	a.visited_on,
	sum( b.amount ) AS amount,
	round(sum( b.amount ) / 7, 2 ) AS average_amount 
FROM
	( SELECT DISTINCT visited_on FROM customer ) a JOIN customer b 
 	ON datediff( a.visited_on, b.visited_on ) BETWEEN 0 AND 6 
WHERE
	a.visited_on >= (SELECT min( visited_on ) FROM customer ) + 6 
GROUP BY
	a.visited_on
```

- 关键字用大写
- `SELECT`分多行
- 括号两边空一格字符
- 短的内查询合为一行
- `JOIN`和`ON`放在一行
- 



## 参考文章

- [在MySQL中实现Rank高级排名函数](https://www.cnblogs.com/caicaizi/p/9803013.html)
-  [MySQL窗口函数简介](https://blog.csdn.net/qq_41080850/article/details/86416106)
-  