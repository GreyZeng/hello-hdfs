package git.snippets.mr.a_wordcount;

import static git.snippets.mr.LocalConfigJob.getLocalJob;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * MapReduce任务的驱动类
 * 可以配置任务执行的参数
 * Mapper、Reducer、分区、分组相关信息
 */
public class WordCountDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //1.创建配置及job对象
        Job job = getLocalJob();
        //2.设置Driver驱动类
        job.setJarByClass(WordCountDriver.class);
        //3.设置Mapper、Reducer对应的类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        //4.设置Mapper输出Key,Value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //5.设置最终输出数据的key,value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // 设置reduce task个数，默认是一个，我们可以设置多个，最后生成的output文件就有多个
        job.setNumReduceTasks(3);
        //6.设置数据输入和输出路径
        FileInputFormat.setInputPaths(job, new Path("./data/data.txt"));
        FileOutputFormat.setOutputPath(job, new Path("./tmp/" + System.currentTimeMillis() + "/output"));
        //7.运行任务
        boolean success = job.waitForCompletion(true);
        if (success) {
            System.out.println("任务执行成功！");
        } else {
            System.out.println("任务执行失败！");
        }
    }
}
