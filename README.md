# 基于Android开发的医疗质量分析系统
来自学校毕业设计，并不能真的检测医疗质量分析，只是学生毕设，里面用到的质量分析都是自己想当然，并不能当真

该项目依旧使用Web Service对SQL Server数据库进行链接读写操作

Web Service部分依旧使用这个<a href = "https://github.com/MrKins/WebService-Conect-SQLServer">WebService</a>，其中的SQL语句做了变化

该项目两个页面（除开使用静态数据的登陆页外）一个页面获取医生的信息（内含医生治疗病人的平均7天出院率，治愈率等等）；一个页面录入病历信息（录入数据包括，病人名字；住院日期；出院日期；治愈与否以及医生ID）

SQL获取医生的医疗质量分析结果（内含分析要素）select语句：
<pre>SELECT doc_name , 
CAST(AVG(CAST(DATEDIFF(DAY , in_date , out_date)AS float)) AS DECIMAL(10,1)) AS date_dif_avg , 
CAST (CAST (CAST (COUNT (CASE WHEN DATEDIFF (DAY , in_date , out_date) <= 7 THEN 1 END) AS float) / CAST (COUNT (DATEDIFF (DAY , in_date , out_date) ) AS float) * 100 AS int) AS varchar) + '%' AS day7_persent , 
CAST (CAST (CAST (COUNT (CASE WHEN cure = 'true' THEN 1 END) AS float) / CAST (COUNT(cure) AS float) * 100 AS int) AS varchar) + '%' AS cure_persent  , 
AVG(disease_lv) AS disease_lv_avg , 
MAX(disease_lv) AS disease_lv_max , 
AVG(CASE 
WHEN DATEDIFF(DAY , in_date , out_date) <= 3 THEN 100 
WHEN DATEDIFF(DAY , in_date , out_date) BETWEEN 4 AND 7 THEN 90 
WHEN DATEDIFF(DAY , in_date , out_date) BETWEEN 8 AND 15 THEN 85 
WHEN DATEDIFF(DAY , in_date , out_date) BETWEEN 16 AND 30 THEN 80 
WHEN DATEDIFF(DAY , in_date , out_date) >= 30 THEN 70 
END)
 + CAST (CAST (COUNT (CASE WHEN cure = 'true' THEN 1 END) AS float) / CAST (COUNT(cure) AS float) * 100 AS int)
 + CAST (CAST (COUNT (CASE WHEN DATEDIFF (DAY , in_date , out_date) <= 7 THEN 1 END) AS float) / CAST (COUNT (DATEDIFF (DAY , in_date , out_date) ) AS float) * 100 AS int) 
 + AVG(CASE 
 WHEN disease_lv >= 9 AND cure = 'true' THEN 300 
 WHEN disease_lv < 9 AND disease_lv >= 7 AND cure = 'true' THEN 250
 WHEN disease_lv < 7 AND disease_lv >= 5 AND cure = 'true' THEN 150
 WHEN disease_lv < 5 AND disease_lv >= 3 AND cure = 'true' THEN 100
 WHEN disease_lv < 3 AND disease_lv >= 1 AND cure = 'true' THEN 80
 END)
 AS score 
FROM Medical.dbo.Patient , Medical.dbo.Doctor 
WHERE Patient.doc_id = Doctor.doc_id 
GROUP BY doc_name 
ORDER BY score;</pre>

SQL插入病历信息insert语句：

<pre>INSERT INTO Medical.dbo.Patient(pat_name , in_date , out_date , doc_id , cure , disease_lv) 
VALUES('K先生' , '2016-02-11' , '2016-03-11' , '102' , '1' , '10');</pre>


登陆页

![Login](https://raw.githubusercontent.com/MrKins/AndroidMedicalQualityProject/master/Image/Login.png)

医生医疗质量页

![Doctor](https://raw.githubusercontent.com/MrKins/AndroidMedicalQualityProject/master/Image/Doctor.png)

录入病例页

![Patient](https://raw.githubusercontent.com/MrKins/AndroidMedicalQualityProject/master/Image/Patient.png)
