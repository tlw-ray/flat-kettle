import airflow
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from airflow.operators.http_operator import SimpleHttpOperator
from datetime import datetime, timedelta

# start_year:Integer{2000-9999} = 2019
# start_month:Integer{1-12} = 8 
# start_day:Integer{1-31} = 12
# timedeltaUnit:Enum{years,months,days,hours,minutes,seconds} = seconds
# name:String=%E9%8D%96%E8%AF%B2%E6%A7%BA%E6%B5%9C%E5%AC%A9%E6%AC%A2%E7%91%99%EF%B9%80%E5%BD%82%E9%91%BE%E5%B3%B0%E5%BD%87%E9%8E%AE%EF%BD%88%EF%BF%BD%E5%91%AC%E4%BF%8A%E9%8E%AD%EF%BF%BD
# id:String=841440b6-e36f-40df-ac6f-a53735f45212

# These args will get passed on to each operator
# You can override them on a per-task basis during operator initialization
default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'start_date': datetime(${year},${month},${day}),
    'end_date': datetime(2999,12,31),
    'email': ['airflow@example.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(seconds=5),
}

dag = DAG(
    'kettle_dag_${name}',
    default_args=default_args,
    schedule_interval=timedelta(minutes=5),
)
# 通过URL调用Kettle计划任务, 
# http://172.16.0.170:8686/kettle/startJob?name=%E9%8D%96%E8%AF%B2%E6%A7%BA%E6%B5%9C%E5%AC%A9%E6%AC%A2%E7%91%99%EF%B9%80%E5%BD%82%E9%91%BE%E5%B3%B0%E5%BD%87%E9%8E%AE%EF%BD%88%EF%BF%BD%E5%91%AC%E4%BF%8A%E9%8E%AD%EF%BF%BD&id=841440b6-e36f-40df-ac6f-a53735f45212

kettle_task_${name} = SimpleHttpOperator(
    task_id='kettle_task_${id}',
    method='GET',
    http_conn_id='kettle_carte',
    endpoint='kettle/startJob',
    data={"name": "${name}", "id": "${id}"},
    dag=dag,
)

kettle_task_${name}