import java.io.IOException;
import java.text.ParseException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Asn2 {
        public static void main(String [] args) throws Exception{
                Configuration c = new Configuration();
                String[] files = new
GenericOptionsParser(c,args).getRemainingArgs();
                Path input=new Path(files[0]);
                Path output=new Path(files[1]);
                Job j=new Job(c,"Asn2");
                j.setJarByClass(Asn2.class);
                j.setMapperClass(MapForWordCount.class);
                j.setReducerClass(ReduceForWordCount.class);
                j.setOutputKeyClass(Text.class);
                j.setOutputValueClass(IntWritable.class);
                FileInputFormat.addInputPath(j, input);
                FileOutputFormat.setOutputPath(j, output);
                System.exit(j.waitForCompletion(true)?0:1);
        }

        public static class MapForWordCount extends
Mapper<LongWritable, Text, Text, IntWritable>{

                public void map(LongWritable key, Text value, Context
con) throws IOException, InterruptedException{
                	IntWritable one = new IntWritable(1);
                	Text word = new Text();
                	StringTokenizer itr = new StringTokenizer(value.toString());
                	while(itr.hasMoreTokens()){
                		word.set(itr.nextToken());
                		con.write(word,one);
                	}
                }
        }

        public static class ReduceForWordCount extends Reducer<Text,
IntWritable, Text, IntWritable>{

                public void reduce(Text key, Iterable<IntWritable>
values, Context con) throws IOException, InterruptedException{
                	IntWritable ans = new IntWritable();
                	int sum = 0;
                	for(IntWritable val: values){
                		sum+=val.get();
                	}
                	ans.set(sum);
                	con.write(key,ans);
                }


        }
}
