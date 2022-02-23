import { Table } from 'antd';

function MyTable(props: any) {

  const columns = [
    {
      title: 'START TIME',
      dataIndex: 'startUTC',
      key: 'startUTC',
      sorter: (a: any, b: any) => a.startUTC - b.startUTC,
      sortDirections: ['ascend', 'descend'],
      render: (val: any) => {
        if (!val) return null;
        const date = new Date(val * 1000);

        return date.toLocaleString('en-US');
      },
    },
    {
      title: 'END TIME',
      dataIndex: 'endUTC',
      key: 'endUTC',
      sorter: (a: any, b: any) => a.endUTC - b.endUTC,
      sortDirections: ['ascend', 'descend'],
      render: (val: any) => {
        if (!val) return null;
        const date = new Date(val * 1000);

        return date.toLocaleString('en-US');
      },
    },
    {
      title: 'DURATION (s)',
      dataIndex: 'duration',
      key: 'duration',
      sorter: (a: any, b: any) => a.startUTC - b.startUTC,
      sortDirections: ['ascend', 'descend'],

    },
  ];
  return <Table
    // @ts-ignore
    columns={columns}
    dataSource={props.dataSource}
    rowKey={record => record?._id}
    pagination={{ defaultPageSize: 3, showSizeChanger: true, pageSizeOptions: ['3', '5', '10']}}
  />;
}

export default MyTable;
